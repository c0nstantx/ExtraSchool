package controllers.service;

import static org.junit.Assert.*;

import java.util.List;

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
}
