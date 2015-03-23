package models.util;

import models.domain.Activity;
import models.domain.EntityBaseTest;

import org.junit.Assert;
import org.junit.Test;

import controllers.service.ActivityService;

public class ActivityStatisticsTest extends EntityBaseTest
{
	@Test
	public void testConstructor()
	{
		ActivityService as = new ActivityService();
		Activity activity = as.findActivityByName("Basketball");
		ActivityStatistics ss = new ActivityStatistics(activity);
		
		Assert.assertNotNull(ss);
	}
	
	@Test
	public void testCalculation()
	{
		ActivityService as = new ActivityService();
		Activity activity = as.findActivityByName("Gymnastics");
		ActivityStatistics ss = new ActivityStatistics(activity);
		
		Assert.assertNotNull(ss);
	}
}
