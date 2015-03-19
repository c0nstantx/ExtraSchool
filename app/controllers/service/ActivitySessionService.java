package controllers.service;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import models.domain.Activity;
import models.domain.ActivitySession;

/**
 * Service for Activity Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class ActivitySessionService extends BaseService {
	
	/**
	 * Creates sessions for a specific activity
	 * @param activity activity to create sessions for
	 * @param startDate starting date for activity sessions
	 * @param endDate closing date for activity sessions
	 * @param selection a boolean array of 7 places, indicating the days of the week when activity sessions are scheduled
	 * @return true if creation successful, false otherwise
	 */
	public boolean createActivitySessions(Activity activity, Date startDate, Date endDate, boolean[] selection) { // tested
		// needs check for clashes
		activity.createSessions(startDate, endDate, selection);
		em.getTransaction().begin();
		em.merge(activity);
		em.getTransaction().commit();
		return true;
	}
	
	/**
	 * Deletes all sessions of a specific activity
	 * Prerequisites:
	 * - No registers must exist for any of the sessions
	 * @param activity
	 * @return true if deletion successful, false otherwise
	 */
	public boolean deleteActivitySessions(Activity activity) { // tested
		List<ActivitySession> sessions = findSessionsByActivity(activity);
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
	 * Find all activity sessions on a given date
	 * @param date date of interest
	 * @return list of sessions
	 */
	public List<ActivitySession> findSessionsByDate(Date date) { // tested
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
	public List<ActivitySession> findSessionsByActivity(Activity activity) { // tested
		@SuppressWarnings("unchecked")
		List<ActivitySession> sessions = em.createQuery("SELECT a FROM ActivitySession a WHERE a.activity.id = :id")
				.setParameter("id", activity.getId())
				.getResultList();
		return sessions;
	}
	
	/**
	 * Find the sessions for the specified activity on the specified date
	 * @return the retrieved session or null if no such session exists
	 */
	public ActivitySession findSessionByActivityDate(Activity activity, Date date) { // tested
		try {
			ActivitySession session = (ActivitySession) em.createQuery("SELECT a FROM ActivitySession a WHERE a.activity.id = :id AND a.date = :date")
					.setParameter("id", activity.getId())
					.setParameter("date", date)
					.getSingleResult();
			return session;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Find all activity sessions on any date
	 * @return list of sessions
	 */
	public List<ActivitySession> findAllSessions() { // tested
		Query query = em.createQuery("SELECT a FROM ActivitySession a");
		@SuppressWarnings("unchecked")
		List<ActivitySession> sessions = query.getResultList();
		return sessions;
	}
}
