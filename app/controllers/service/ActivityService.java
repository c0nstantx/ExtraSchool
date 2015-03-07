package controllers.service;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.User;

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
		if (findByName(name) != null) {
			System.out.println("Activity '" + name + "' already Exists");
			return null;
		}
		Activity activity = new Activity(name, description, venue);
		em.getTransaction().begin();
		em.persist(activity);
		em.getTransaction().commit();
		return activity;
	}
	
	public boolean updateActivity() {
		
	}
	
	public boolean deleteActivity() {
		
	}
	
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
