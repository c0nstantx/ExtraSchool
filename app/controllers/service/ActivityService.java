package controllers.service;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import models.domain.Activity;
import models.domain.ActivitySession;

/**
 * Service for Activity Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 *
 */
public class ActivityService extends BaseService {
	
	/**
	 * Finds all activities
	 * @return list of activities
	 * 
	 */
	public List<Activity> findAll() {
		@SuppressWarnings("unchecked")
		List<Activity> results = em.createQuery("SELECT a FROM Activity a").getResultList();
		return results;
	}

	/**
	 * Find an activity by its name
	 * @param name activity name
	 * @return activity with the specified name or null
	 * 
	 */
	public Activity findByName(String name) {
		Activity activity = (Activity) em.createQuery("SELECT a FROM Activity a WHERE name = :name")
				.setParameter("name", name)
				.getSingleResult();
		return activity;
	}
	
	/**
	 * Find all activities that have sessions on a given date
	 * @param date date of interest
	 * @return list of activities
	 * 
	 */
	public List<Activity> findByDate(Date date) {
		Query query = em.createQuery("SELECT a FROM Activity a JOIN a.sessions ac WHERE ac.date = :date")
				.setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<Activity> activities = query.getResultList();
		return activities;
	}
	
	/**
	 * Find all activity sessions on a given date
	 * @param date date of interest
	 * @return list of sessions
	 * 
	 */
	public List<ActivitySession> findSessionsByDate(Date date) {
		Query query = em.createQuery("SELECT a FROM ActivitySession a WHERE a.date = :date")
				.setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<ActivitySession> sessions = query.getResultList();
		return sessions;
	}
	
	/**
	 * Find all activity sessions on any date
	 * @return list of sessions
	 * 
	 */
	public List<ActivitySession> findAllSessions() {
		Query query = em.createQuery("SELECT a FROM ActivitySession a");
		@SuppressWarnings("unchecked")
		List<ActivitySession> sessions = query.getResultList();
		return sessions;
	}
}
