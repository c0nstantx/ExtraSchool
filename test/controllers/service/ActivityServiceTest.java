package controllers.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import models.domain.Activity;
import models.domain.EntityBaseTest;
import models.domain.User;
import models.util.DateLib;
import models.util.SessionStatistics;

import org.junit.Test;

public class ActivityServiceTest extends EntityBaseTest
{
	private ActivityService as;

	@Override
	public void beforeTest() {
		super.beforeTest();
		as = new ActivityService();
	}
	
	// Testing ActivityService.createActivity(), ActivityService.createActivitySessions()
	@Test
	public void addNewActivities() {
		
		// Add 3 new activities (3 already in database)
		Activity volleyAc = as.createActivity("Volleyball", "Volleyball for beginners", "Inside volleyball court");
		Activity handballAc = as.createActivity("Handball", "Advanced handball training", "Outside pitch");
		Activity photoAc = as.createActivity("Photography club", "Let's take pictures!", "Room B4");
		
		// The total number of activities should now be 7
		assertEquals(7, as.findAllActivities().size());
		
		// Create sessions for above activities
		ActivitySessionService ass = new ActivitySessionService();
		ass.createActivitySessions(volleyAc, DateLib.getDateObject(1, 4, 2015, 16, 0, 0), DateLib.getDateObject(30, 4, 2015),
				new boolean[] {false, true, false, false, false, false, false}); // during the month of April, on Mondays at 16.00
		ass.createActivitySessions(handballAc, DateLib.getDateObject(1, 4, 2015, 17, 0, 0), DateLib.getDateObject(30, 4, 2015),
				new boolean[] {false, false, true, true, false, false, false}); // during the month of April, on Tuesdays and Wednesdays at 17.00
		ass.createActivitySessions(photoAc, DateLib.getDateObject(1, 5, 2015), DateLib.getDateObject(31, 5, 2015),
				new boolean[] {false, false, false, false, false, false, true}); // during the month of May, on Saturdays at 12.00
		
		// Verify
		assertEquals(4, volleyAc.getSessions().size()); // 4 Mondays in April 2015
		assertEquals(9, handballAc.getSessions().size()); // 9 Tuesdays & Wednesdays in April 2015
		assertEquals(5, photoAc.getSessions().size()); // 5 Saturdays in May 2015
		
		// Create sessions for the volleyball activity again
		ass.createActivitySessions(volleyAc, DateLib.getDateObject(1, 5, 2015, 16, 0, 0), DateLib.getDateObject(13, 5, 2015),
				new boolean[] {false, true, false, false, false, false, false}); // add sessions for the first two Mondays in May
		assertEquals(6, volleyAc.getSessions().size()); // should now be 6
	}
	
	// Testing ActivityService.createActivity()
	@Test
	public void addNewExistingActivities() {
		
		// Add activity with duplicate name
		as.createActivity("Drama", "Ancient Greek drama course", "Room E1");
		
		// This activity should not have been added
		assertEquals(4, as.findAllActivities().size());
	}
	
	// Testing ActivityService.updateActivity()
	@Test
	public void updateActivity() {
		
		// Retrieve activity from database
		Activity dramaAc = as.findActivityByName("Drama");
		
		// Change room and elaborate description
		String newDescription = "A delightful journey through the works of the great English play writer. Don't miss it!";
		String newRoom = "Auditorium";
		dramaAc.setDescription(newDescription);
		dramaAc.setVenue(newRoom);
		as.updateActivity(dramaAc);
		
		// Retrieve updated activity from database and check
		Activity dramaAcUpd = as.findActivityByName("Drama");
		assertTrue(dramaAcUpd.getDescription().equals(newDescription));
		assertTrue(dramaAcUpd.getVenue().equals(newRoom));
		
		/* Update a non persisted activity */
		Activity transparentActivity = new Activity();
		transparentActivity.setName("MockName");
		assertFalse(as.updateActivity(transparentActivity));
	}
	
	// Testing ActivityService.deleteActivity() and ActivityService.deleteActivitySessions() (called by the former)
	@Test
	public void deleteActivity() {
		
		// Retrieve basketball activity from database
		Activity basketAc = as.findActivityByName("Basketball");
		
		// This activity has 2 enrolled users, it should not be deleted
		assertFalse(as.deleteActivity(basketAc));
		assertNotNull(as.findActivityByName("Basketball"));
		
		// Retrieve drama activity from database
		Activity dramaAc = as.findActivityByName("Drama");
				
		// This activity has no enrolled users, it should be deleted
		assertTrue(as.deleteActivity(dramaAc));
		assertNull(as.findActivityByName("Drama"));
	}
	
	// Testing ActivityService.findAllActivities(), ActivityService.findActivityByName(), ActivityService.findActivitiesByDate()
	@Test
	public void findActivities()
	{
		List<Activity> activities = as.findAllActivities();
		assertEquals(4, activities.size());
		
		Activity activity = as.findActivityByName("Gymnastics");
		assertNotNull(activity);
		
		activities = as.findActivitiesByDate(DateLib.getDateObject(3, 3, 2015));
		assertEquals(1, activities.size());
	}
	
	// Testing ActivityService.findActivityTutor()
	@Test
	public void findActivityTutor() {
		
		// No tutors are registered for this activity initially
		assertNull(as.findActivityTutor("Drama"));
		
		// Retrieve activity "Drama"
		Activity a = as.findActivityByName("Drama"); // no memberships
		
		// Retrieve tutor "kchristofilos"
		UserService us = new UserService();
		User tutor = us.findUserByUsername("kchristofilos"); // no memberships
		
		// Create new membership for activity a3 and user tutor
		MembershipService ms = new MembershipService();
		ms.createMembership(a, tutor);
		
		// Verify
		assertEquals(1, a.getMemberships().size());
		assertEquals(1, tutor.getMemberships().size());
		
		// Retrieve tutor using ActivityService
		User searchTutor = as.findActivityTutor(a.getName());
		assertEquals(tutor.getId(), searchTutor.getId());
		
		/* Test non existing activity */
		assertNull(as.findActivityTutor("NonExisting"));
	}

	@Test
	public void testFindById()
	{
		assertNull(as.find(-1));
	}

	@Test
	public void testStats()
	{
		Activity activity = as.findActivityByName("Gymnastics");
		SessionStatistics stats = as.calculateRegistrationStatistics(activity);
		
		assertNotNull(stats);
	}
}
