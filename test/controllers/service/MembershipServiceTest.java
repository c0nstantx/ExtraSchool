package controllers.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import models.domain.Activity;
import models.domain.EntityBaseTest;
import models.domain.User;
import models.persistence.UserType;
import models.util.DateLib;

import org.junit.Test;

public class MembershipServiceTest extends EntityBaseTest
{
	private MembershipService ms;

	@Override
	public void beforeTest() {
		super.beforeTest();
		ms = new MembershipService();
	}
	
	@Test
	public void createNewMemberships() {
		
		// Retrieve all activities from database (3 in total)
		ActivityService as = new ActivityService();
		Activity a1 = as.findActivityByName("Gymnastics");
		Activity a2 = as.findActivityByName("Basketball");
		Activity a3 = as.findActivityByName("Drama");
		
		// Retrieve all users from database (3 in total)
		UserService us = new UserService();
		User u1 = us.findUserByUsername("kchristofilos");
		
		// Enroll user u1 in all activities
		ms.createMembership(a1, u1); // this is possible
		ms.createMembership(a2, u1); // this is possible
		ms.createMembership(a3, u1); // this is not possible (clash on 10/03/2015 12:00)
		
		// Verify that u1 was only registered for 2 activities
		assertEquals(2, u1.getMemberships().size());
		
		// Create new tutor and add to activity a2
		User tutor = us.createUser("pete", "pete123", UserType.Tutor, "Peter", "Cushing", DateLib.getDateObject(12, 03, 1966));
		assertNull(ms.createMembership(a2, tutor)); // this should fail, there is already a tutor in a2
		
		// Create new admin and add to activity a3
		User admin = us.createUser("stuart", "lalala", UserType.Admin, "Stuart", "Taylor", DateLib.getDateObject(30, 01, 1969));
		assertNull(ms.createMembership(a3, admin)); // this should fail, cannot add administrators to activities
		
		// Add u2 to activity a1
		User u2 = us.findUserByUsername("sok");
		assertNull(ms.createMembership(a1, u2)); // this should fail, u2 is already registered for a1
	}
}
