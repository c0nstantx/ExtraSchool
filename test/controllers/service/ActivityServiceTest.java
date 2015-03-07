package controllers.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.EntityBaseTest;
import models.util.DateLib;

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
		assertEquals(3, activities.size());
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
		List<Activity> activities = as.findByDate(DateLib.getDateObject(3, 3, 2015));
		assertEquals(1, activities.size());
	}
	
	@Test
	public void findSessionsByDate()
	{
		List<ActivitySession> sessions = as.findSessionsByDate(DateLib.getDateObject(10, 3, 2015));
		System.out.println(sessions);
		assertEquals(2, sessions.size());
	}
	
	@Test
	public void testActivitySessions() {
		Activity a3 = as.findByName("activity3");
		assertNotNull(a3);
		assertEquals(9, a3.getSessions().size());
	}
}
