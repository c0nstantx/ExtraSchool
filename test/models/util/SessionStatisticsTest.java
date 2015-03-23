package models.util;

import java.util.Set;

import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.EntityBaseTest;
import models.domain.SessionStatus;

import org.junit.Assert;
import org.junit.Test;

import controllers.service.ActivityService;

public class SessionStatisticsTest extends EntityBaseTest
{
	@Test
	public void testConstructor()
	{
		ActivityService as = new ActivityService();
		Activity activity = as.findActivityByName("Basketball");
		SessionStatistics ss = new SessionStatistics(activity);
		
		Assert.assertNotNull(ss);
	}
	
	@Test
	public void testCalculation()
	{
		ActivityService as = new ActivityService();
		Activity activity = as.findActivityByName("Gymnastics");
		SessionStatistics ss = new SessionStatistics(activity);
		
		Assert.assertNotNull(ss);
	}
}
