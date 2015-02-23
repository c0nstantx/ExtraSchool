package controllers.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Date;

import models.domain.Activity;
import models.domain.EntityBaseTest;

import org.junit.Test;

public class ActivityServiceTest extends EntityBaseTest
{
	private ActivityService as;

	@Override
	public void beforeTest()
	{
		super.beforeTest();
		as = new ActivityService();
	}
	
	@Test
	public void findAllActivities()
	{
		List<Activity> activities = as.findAll();
		assertEquals(2, activities.size());
	}

	@Test
	public void findActivityByName()
	{
		Activity activity = as.findByName("activity1");
		assertNotNull(activity);
	}

	@Test
	public void findActivitiesByDate()
	{
		@SuppressWarnings("deprecation")
		List<Activity> activities = as.findByDate(new Date("2 Mar 2015"));
		assertEquals(1, activities.size());
	}
}
