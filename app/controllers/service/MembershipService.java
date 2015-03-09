package controllers.service;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.Membership;
import models.domain.User;
import models.persistence.UserType;
import models.util.DateLib;

/**
 * Service for User Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class MembershipService extends BaseService {
	/**
	 * Calls createMembership(Activity, User, Date)
	 * By default passes current system date
	 */
	public Membership createMembership(Activity activity, User user) { // tested
		return createMembership(activity, user, DateLib.getDateObject());
	}
	
	/**
	 * Creates new Membership object and attaches it to the database
	 * Prerequisites:
	 * - the activity must have at least one session
	 * - the user cannot be an administrator
	 * - this user is not already signed up for this activity
	 * - only one tutor can participate in each activity
	 * - this activity's sessions must not clash with any sessions of other activities
	 *   that the user is already registered for
	 * @param activity the activity to sign up for
	 * @param user the user to sign up for the activity
	 * @param registrationDate the date of enrollment
	 * @return Membership|null The created Membership object or null
	 */
	public Membership createMembership(Activity activity, User user, Date registrationDate) { // tested
		if (activity.getSessions().size() == 0) {
			return null;
		}
		if (user.getUserType() == UserType.Admin) {
			return null;
		}
		if (findMembership(activity, user) != null) {
			return null;
		}
		ActivityService as = new ActivityService();
		if (as.findActivityTutor(activity.getName()) != null) {
			return null;
		}
		if (detectClashes(activity, user)) {
			return null;
		}
		Membership membership = new Membership(activity, user, registrationDate);
		activity.addMembership(membership);
		user.addMembership(membership);
		em.getTransaction().begin();
		em.persist(membership);
		em.getTransaction().commit();
		return membership;
	}
	
	/**
	 * Deletes membership
	 * Prerequisites:
	 * - there is no published report for this membership
	 * - there are no registers linked to the user for any of the sessions of the activity
	 * @param membership the membership to delete
	 * @return true if deletion successful, false otherwise
	 */
	public boolean deleteMembership(Membership membership) { // tested
		Membership searchMembership = findMembership(membership.getActivity(), membership.getUser());
		if (searchMembership != null) {
			if (searchMembership.getReport().getPublished()) {
				return false;
			}
			SessionRegisterService srs = new SessionRegisterService();
			if (srs.findRegistersByActivityUser(membership.getActivity(), membership.getUser()).size() > 0) {
				return false;
			}
			em.getTransaction().begin();
			em.createQuery("DELETE FROM Membership m WHERE m.id = :id")
			.setParameter("id", membership.getId())
			.executeUpdate();
			em.getTransaction().commit();
			return true;
		}
		return true; // deletion is considered successful if there was no such membership in the first place
	}
	
	/**
	 * Assigns a report for a specific membership (existing report is overwritten, published is always set to false)
	 * @param membership the user-activity pair for which the report is written
	 * @param grade the grade assigned
	 * @param comment the comment written
	 */
	public void assignReport(Membership membership, double grade, String comment) { // tested
		Membership searchMembership = findMembership(membership.getActivity(), membership.getUser());
		if (searchMembership != null) {
			searchMembership.getReport().setGrade(grade);
			searchMembership.getReport().setComment(comment);
			searchMembership.getReport().setPublished(false);
			em.getTransaction().begin();
			em.merge(searchMembership);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Publishes the report for the specific membership
	 * @param membership the (user, activity) pair that the report concerns
	 * @param publicationDate publication date
	 */
	public void publishReport(Membership membership, Date publicationDate) { // tested
		Membership searchMembership = findMembership(membership.getActivity(), membership.getUser());
		if (searchMembership != null) {
			searchMembership.getReport().setPublicationDate(publicationDate);
			searchMembership.getReport().setPublished(true);
			em.getTransaction().begin();
			em.merge(searchMembership);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Finds all memberships for a specific user
	 * @param user user
	 * @return list of memberships
	 */
	public List<Membership> findMembershipsByUser(User user) { // tested
		@SuppressWarnings("unchecked")
		List<Membership> results = em.createQuery("SELECT m FROM Membership m WHERE m.user.id = :id")
		.setParameter("id", user.getId())
		.getResultList();
		return results;
	}
	
	/**
	 * Finds all memberships for a specific activity
	 * @param activity activity
	 * @return list of memberships
	 */
	public List<Membership> findMembershipsByActivity(Activity activity) { // tested
		@SuppressWarnings("unchecked")
		List<Membership> results = em.createQuery("SELECT m FROM Membership m WHERE m.activity.id = :id")
		.setParameter("id", activity.getId())
		.getResultList();
		return results;
	}

	/**
	 * Find a membership by its key (activity, user)
	 * @param activity activity
	 * @param user user
	 * @return membership with the specified key or null
	 */
	public Membership findMembership(Activity activity, User user) { // tested
		try {
			Membership membership = (Membership)em
					.createQuery("SELECT m FROM Membership m WHERE m.activity.id = :acId AND m.user.id = :usId")
					.setParameter("acId", activity.getId())
					.setParameter("usId", user.getId())
					.getSingleResult();
			return membership;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/*
	 * Detects if there are clashes between one activity's sessions and the
	 * sessions of all the other activities the user is already signed up for
	 */
	private boolean detectClashes(Activity activity, User user) {
		List<Membership> existingMemberships = findMembershipsByUser(user);
		ActivitySessionService ass = new ActivitySessionService();
		for (Membership membership : existingMemberships) {
			List<ActivitySession> sessions = ass.findSessionsByActivity(membership.getActivity());
			for (ActivitySession session : sessions) {
				if (detectClashes(ass, session, activity)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * Detects if there are clashes between the sessions of a new activity and a
	 * session the user is already attending  
	 */
	private boolean detectClashes(ActivitySessionService sessionService, ActivitySession existingSession, Activity newActivity) {
		List<ActivitySession> newSessions = sessionService.findSessionsByActivity(newActivity);
		for (ActivitySession newSession : newSessions) {
			if (DateLib.equals(newSession.getDate(), existingSession.getDate())) {
				return true;
			}
		}
		return false;
	}
}
