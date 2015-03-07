package controllers.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import models.domain.Activity;
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
	public void addNewActivities() {
		
	}
	
	@Test
	public void addNewExistingActivities() {
		
	}
	
	@Test
	public void deleteActivity() {
		
	}
	
	@Test
	public void UpdateActivity() {
		
	}
	
	@Test
	public void findAllActivities()
	{
		List<Activity> activities = as.findAllActivities();
		assertEquals(3, activities.size());
	}

	@Test
	public void findActivityByName()
	{
		Activity activity = as.findActivityByName("activity1");
		assertNotNull(activity);
	}

	@Test
	public void findActivitiesByDate()
	{
		List<Activity> activities = as.findActivitiesByDate(DateLib.getDateObject(3, 3, 2015));
		assertEquals(1, activities.size());
	}
}
