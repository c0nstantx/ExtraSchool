package controllers.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.EntityBaseTest;
import models.domain.User;
import models.util.DateLib;

import org.junit.Test;

public class ActivityServiceTest extends EntityBaseTest
{
	private ActivityService as;

	@Override
	public void beforeTest() {
		super.beforeTest();
		as = new ActivityService();
	}
	
	@Test
	public void addNewActivities() {
		
		// Add 3 new activities (3 already in database)
		as.createActivity("Volleyball", "Volleyball for beginners", "Inside volleyball court");
		as.createActivity("Handball", "Advanced handball training", "Outside pitch");
		as.createActivity("Photography club", "Let's take pictures!", "Room B4");
		
		// The total number of activities should now be 6
		assertEquals(6, as.findAllActivities().size());
	}
	
	@Test
	public void addNewExistingActivities() {
		
		// Add activity with duplicate name
		as.createActivity("Drama", "Ancient Greek drama course", "Room E1");
		
		// This activity should not have been added
		assertEquals(3, as.findAllActivities().size());
	}
	
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
	}
	
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
	
	@Test
	public void findAllActivities()
	{
		List<Activity> activities = as.findAllActivities();
		assertEquals(3, activities.size());
	}

	@Test
	public void findActivityByName()
	{
		Activity activity = as.findActivityByName("Gymnastics");
		assertNotNull(activity);
	}

	@Test
	public void findActivitiesByDate()
	{
		List<Activity> activities = as.findActivitiesByDate(DateLib.getDateObject(3, 3, 2015));
		assertEquals(1, activities.size());
	}
	
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
	}
	
	@Test
	public void findSessionsByDate()
	{
		List<ActivitySession> sessions = as.findSessionsByDate(DateLib.getDateObject(10, 3, 2015));
		assertEquals(2, sessions.size());
	}
}
