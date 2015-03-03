package controllers.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import models.domain.Activity;
import models.domain.EntityBaseTest;
import models.persistence.CalendarUtilities;

import org.junit.Test;

public class ActivityServiceTest extends EntityBaseTest
{
	private ActivityService as;

	@Override
	public void beforeTest()
	{
		System.out.println("ActivityServiceTest: beforeTest()");
	
		super.beforeTest();
		as = new ActivityService();
	}
	
	@Test
	public void findAllActivities()
	{
		System.out.println("ActivityServiceTest: findAllActivities()");
		
		List<Activity> activities = as.findAll();
		assertEquals(2, activities.size());
	}

	@Test
	public void findActivityByName()
	{
		System.out.println("ActivityServiceTest: findActivityByName()");
		
		Activity activity = as.findByName("activity1");
		assertNotNull(activity);
	}

	@Test
	public void findActivitiesByDate()
	{
		System.out.println("ActivityServiceTest: findActivitiesByDate()");
		
		List<Activity> activities = as.findByDate(CalendarUtilities.getDate(3, 3, 2015));
		assertEquals(1, activities.size());
	}

}
