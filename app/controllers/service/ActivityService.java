package controllers.service;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import models.domain.Activity;

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
			System.out.println("Activity '" + name + "' already Exists");
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
	 * @return boolean
	 */
	public boolean updateActivity(Activity activity) {
		Activity searchActivity = findActivityByName(activity.getName());
		if (activity.getId() == searchActivity.getId()) {
			em.getTransaction().begin();
			em.merge(activity);
			em.getTransaction().commit();
			return true;
		}
		return false;
	}
	
	/**
	 * Deletes activity
	 * @param activity
	 */
	public void deleteActivity(Activity activity) {
		if (findActivityByName(activity.getName()) != null) {
			em.getTransaction().begin();
			em.createQuery("DELETE FROM Activity a WHERE a.id = :id")
			.setParameter("id", activity.getId())
			.executeUpdate();
			em.getTransaction().commit();
		}
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
		Activity activity = (Activity) em.createQuery("SELECT a FROM Activity a WHERE name = :name")
				.setParameter("name", name)
				.getSingleResult();
		return activity;
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
}
