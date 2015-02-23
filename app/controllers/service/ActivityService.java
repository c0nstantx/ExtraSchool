package controllers.service;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import models.domain.Activity;

/**
 * Service for Activity Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
public class ActivityService extends BaseService
{
	/**
	 * Find all activities
	 * @return
	 */
	public List<Activity> findAll()
	{
		@SuppressWarnings("unchecked")
		List<Activity> results = em.createQuery("SELECT a FROM Activity a").getResultList();
		System.out.println(results.toString());
		return results;
	}

	/**
	 * Find an activity by it's name
	 * @param name
	 * @return
	 */
	public Activity findByName(String name)
	{
		Activity activity = (Activity) em.createQuery("SELECT a FROM Activity a WHERE name = :name")
				.setParameter("name", name)
				.getSingleResult();
		return activity;
	}
	
	/**
	 * Find a list of activities of a given date
	 * @param date
	 * @return
	 */
	public List<Activity> findByDate(Date date)
	{
		Query query = em.createQuery("SELECT a FROM Activity a JOIN a.calendar ac WHERE ac.date = :date")
				.setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<Activity> activities = query.getResultList();
		return activities;
	}
}
