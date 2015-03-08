package controllers.service;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.User;
import models.persistence.UserType;

/**
 * Service for Activity Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 *
 */
public class ActivityService extends BaseService {
	
	/**
	 * Creates new Activity object and attaches it to the database
	 * Prerequisites:
	 * - no other activity exists with the same name
	 * @param name the name of this activity
	 * @param description a short description of the activity
	 * @param venue the venue where the activity sessions take place
	 * @return Activity|null The created Activity object or null
	 */
	public Activity createActivity(String name, String description, String venue) {
		if (findActivityByName(name) != null) {
			return null;
		}
		Activity activity = new Activity(name, description, venue);
		em.getTransaction().begin();
		em.persist(activity);
		em.getTransaction().commit();
		return activity;
	}
	
	/**
	 * Updates activity
	 * @param activity
	 * @return true if update successful, false otherwise
	 */
	public boolean updateActivity(Activity activity) {
		Activity searchActivity = findActivityByName(activity.getName());
		if (searchActivity != null && activity.getId() == searchActivity.getId()) {
			em.getTransaction().begin();
			em.merge(activity);
			em.getTransaction().commit();
			return true;
		}
		return false;
	}
	
	/**
	 * Deletes a specific activity along with all its individual sessions
	 * Prerequisites:
	 * - No users must be signed up for this activity (its Membership objects list must be empty)
	 * @param activity
	 * @return true if deletion successful, false otherwise
	 */
	public boolean deleteActivity(Activity activity) {
		Activity searchActivity = findActivityByName(activity.getName());
		if (searchActivity != null && searchActivity.getMemberships().size() == 0) {
			if (deleteActivitySessions(searchActivity)) {
				em.getTransaction().begin();
				em.createQuery("DELETE FROM Activity a WHERE a.id = :id")
					.setParameter("id", activity.getId())
					.executeUpdate();
				em.getTransaction().commit();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Deletes all sessions of a specific activity
	 * Prerequisites:
	 * - No registers must exist for any of the sessions
	 * @param activity
	 * @return true if deletion successful, false otherwise
	 */
	public boolean deleteActivitySessions(Activity activity) {
		List<ActivitySession> sessions = findAllSessionsByActivity(activity);
		// if no sessions exist for this activity, deletion is considered successful
		if (sessions == null) {
			return true;
		}
		
		// if there are any registers in any of the sessions, deletion cannot proceed
		for (ActivitySession session : sessions) {
			if (session.getRegisters().size() > 0) {
				return false;
			}
		}
		
		// delete all sessions
		for (ActivitySession session : sessions) {
			em.getTransaction().begin();
			em.createQuery("DELETE FROM ActivitySession a WHERE a.id = :id")
				.setParameter("id", session.getId())
				.executeUpdate();
			em.getTransaction().commit();
		}
		
		return true;
	}
	
	/**
	 * Finds all activities
	 * @return list of activities
	 */
	public List<Activity> findAllActivities() {
		@SuppressWarnings("unchecked")
		List<Activity> results = em.createQuery("SELECT a FROM Activity a").getResultList();
		return results;
	}

	/**
	 * Find an activity by its name
	 * @param name activity name
	 * @return activity with the specified name or null
	 */
	public Activity findActivityByName(String name) {
		try {
			Activity activity = (Activity) em.createQuery("SELECT a FROM Activity a WHERE name = :name")
					.setParameter("name", name)
					.getSingleResult();
			return activity;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Find all activities that have sessions on a given date
	 * @param date date of interest
	 * @return list of activities
	 */
	public List<Activity> findActivitiesByDate(Date date) {
		Query query = em.createQuery("SELECT a FROM Activity a JOIN a.sessions ac WHERE ac.date = :date")
				.setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<Activity> activities = query.getResultList();
		return activities;
	}
	
	/**
	 * Find the tutor for an activity
	 * @param name activity name
	 * @return activity tutor or null
	 */
	public User findActivityTutor(String name) {
		Activity searchActivity = findActivityByName(name);
		if (searchActivity == null) {
			return null;
		}
		try {
			User user = (User)em
					.createQuery("SELECT m.user FROM Membership m WHERE m.activity.id = :id AND m.user.userType = :userType")
					.setParameter("id", searchActivity.getId())
					.setParameter("userType", UserType.Tutor)
					.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Find all activity sessions on a given date
	 * @param date date of interest
	 * @return list of sessions
	 */
	public List<ActivitySession> findSessionsByDate(Date date) {
		Query query = em.createQuery("SELECT a FROM ActivitySession a WHERE a.date = :date")
				.setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<ActivitySession> sessions = query.getResultList();
		return sessions;
	}
	
	/**
	 * Find all sessions for a specific activity on any date
	 * @return list of sessions
	 */
	public List<ActivitySession> findAllSessionsByActivity(Activity activity) {
		@SuppressWarnings("unchecked")
		List<ActivitySession> sessions = em.createQuery("SELECT a FROM ActivitySession a WHERE a.activity.id = :id")
				.setParameter("id", activity.getId())
				.getResultList();
		return sessions;
	}
	
	/**
	 * Find all activity sessions on any date
	 * @return list of sessions
	 */
	public List<ActivitySession> findAllSessions() {
		Query query = em.createQuery("SELECT a FROM ActivitySession a");
		@SuppressWarnings("unchecked")
		List<ActivitySession> sessions = query.getResultList();
		return sessions;
	}
}
