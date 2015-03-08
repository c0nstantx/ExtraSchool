package controllers.service;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.Membership;
import models.domain.SessionRegister;
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
	public Membership createMembership(Activity activity, User user) {
		return createMembership(activity, user, DateLib.getDateObject());
	}
	
	/**
	 * Creates new Membership object and attaches it to the database
	 * Prerequisites:
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
	public Membership createMembership(Activity activity, User user, Date registrationDate) {
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
	 * - there are no registers linked to the user for any of the sessions of the activity
	 * - there is no published report for this membership
	 * @param membership
	 * @return true if deletion successful, false otherwise
	 */
	public boolean deleteMembership(Membership membership) {
		if (findMembership(membership.getActivity(), membership.getUser()) != null) {
			em.getTransaction().begin();
			em.createQuery("DELETE FROM Membership m WHERE m.id = :id")
			.setParameter("id", membership.getId())
			.executeUpdate();
			em.getTransaction().commit();
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param membership
	 * @param grade
	 * @param comment
	 */
	public void assignReport(Membership membership, double grade, String comment) {
		Membership searchMembership = findMembership(membership.getActivity(), membership.getUser());
		if (searchMembership != null && membership.getId() == searchMembership.getId()) {
			em.getTransaction().begin();
			em.merge(membership);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * 
	 * @param membership
	 */
	public void publishReport(Membership membership) {
		
	}
	
	/**
	 * Finds all memberships for a specific user
	 * @param user user
	 * @return list of memberships
	 */
	public List<Membership> findAllMembershipsByUser(User user) {
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
	public List<Membership> findAllMembershipsByActivity(Activity activity) {
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
	public Membership findMembership(Activity activity, User user) {
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
		List<Membership> existingMemberships = findAllMembershipsByUser(user);
		ActivityService as = new ActivityService();
		for (Membership membership : existingMemberships) {
			List<ActivitySession> sessions = as.findAllSessionsByActivity(membership.getActivity());
			for (ActivitySession session : sessions) {
				if (detectClashes(as, session, activity)) {
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
	private boolean detectClashes(ActivityService activityService, ActivitySession existingSession, Activity newActivity) {
		List<ActivitySession> newSessions = activityService.findAllSessionsByActivity(newActivity);
		for (ActivitySession newSession : newSessions) {
			if (DateLib.equals(newSession.getDate(), existingSession.getDate())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Finds all session registers for a specific user
	 * @param user user
	 * @return list of registers
	 */
	public List<SessionRegister> findAllRegistersByUser(User user) {
		@SuppressWarnings("unchecked")
		List<SessionRegister> results = em.createQuery("SELECT s FROM SessionRegister s WHERE s.user.id = :id")
			.setParameter("id", user.getId())
			.getResultList();
		return results;
	}
	
	/**
	 * Finds all session registers for a specific activity
	 * @param activity activity
	 * @return list of registers
	 */
	public List<SessionRegister> findAllRegistersByActivity(Activity activity) {
		@SuppressWarnings("unchecked")
		List<SessionRegister> results = em.createQuery("SELECT s FROM SessionRegister s WHERE s.activity.id = :id")
		.setParameter("id", activity.getId())
		.getResultList();
		return results;
	}

	/**
	 * Find a session register by its key (activity session, user)
	 * @param session activity session
	 * @param user user
	 * @return register with the specified key or null
	 */
	public SessionRegister findRegister(ActivitySession session, User user) {
		try {
			SessionRegister register = (SessionRegister)em
					.createQuery("SELECT s FROM SessionRegister s WHERE s.session.id = :seId AND s.user.id = :usId")
					.setParameter("seId", session.getId())
					.setParameter("usId", user.getId())
					.getSingleResult();
			return register;
		} catch (NoResultException e) {
			return null;
		}
	}
}
