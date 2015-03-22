package controllers.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.EntityBaseTest;
import models.util.DateLib;

import org.junit.Assert;
import org.junit.Test;

public class ActivitySessionServiceTest extends EntityBaseTest
{
	private ActivitySessionService ass;

	@Override
	public void beforeTest() {
		super.beforeTest();
		ass = new ActivitySessionService();
	}
	
	// Testing ActivityService.findSessionsByDate(), ActivityService.findSessionsByActivity(), ActivityService.findSessionByActivityDate(), ActivityService.findAllSessions()
	@Test
	public void findSessions()
	{
		ActivityService as = new ActivityService();
		List<ActivitySession> sessions = ass.findSessionsByDate(DateLib.getDateObject(10, 3, 2015)); // find all sessions for a specific date
		assertEquals(2, sessions.size()); // verify
		Activity dramaAc = as.findActivityByName("Drama");
		sessions = ass.findSessionsByActivity(dramaAc); // find all sessions for a specific activity
		assertEquals(9, sessions.size()); // verify
		sessions = ass.findAllSessions(); // find all sessions for a specific activity
		assertEquals(13, sessions.size()); // verify
		
		Activity gymAc = as.findActivityByName("Gymnastics"); // retrieve Gymnastics activity
		ActivitySession session1 = ass.findSessionByActivityDate(gymAc, DateLib.getDateObject(2, 3, 2015)); // this session exists
		assertNotNull(session1); // verify
		ActivitySession session2 = ass.findSessionByActivityDate(gymAc, DateLib.getDateObject(4, 3, 2015)); // this session does not exist
		assertNull(session2); // verify
	}

	@Test
	public void testDeleteActivitySessions()
	{
		ActivityService as = new ActivityService();
		ActivitySessionService ass = new ActivitySessionService();
		
		Activity activity = as.findActivityByName("Drama");
		Assert.assertTrue(ass.deleteActivitySessions(activity));
		
		Activity activity1 = as.findActivityByName("Gymnastics");
		Assert.assertFalse(ass.deleteActivitySessions(activity1));
		
		Activity activity2 = as.findActivityByName("Empty Activity");
		Assert.assertTrue(ass.deleteActivitySessions(activity2));
	}
}
