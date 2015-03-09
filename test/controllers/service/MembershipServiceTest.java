package controllers.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import models.domain.Activity;
import models.domain.EntityBaseTest;
import models.domain.Membership;
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
	
	// Test MembershipService.createMembership() (both overloaded methods, the latter is called by the former)
	@Test
	public void createNewMemberships() {
		
		// Retrieve all activities from database (3 in total)
		ActivityService as = new ActivityService();
		Activity a1 = as.findActivityByName("Gymnastics");
		Activity a2 = as.findActivityByName("Basketball");
		Activity a3 = as.findActivityByName("Drama");
		
		// Retrieve user u1 from database
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
		assertNull(ms.createMembership(a2, tutor)); // creation should fail, there is already a tutor in a2
		
		// Create new admin and add to activity a3
		User admin = us.createUser("stuart", "lalala", UserType.Admin, "Stuart", "Taylor", DateLib.getDateObject(30, 01, 1969));
		assertNull(ms.createMembership(a3, admin)); // creation should fail, cannot add administrators to activities
		
		// Add u2 to activity a1
		User u2 = us.findUserByUsername("sok");
		assertNull(ms.createMembership(a1, u2)); // creation should fail, u2 is already registered for a1
		
		// Create a new activity and a new user
		Activity newAc = as.createActivity("History", "TBD", "TBD");
		User newUser = us.createUser("newbie2015", "123", UserType.Student, "Jason", "Perkins", DateLib.getDateObject(21, 10,  1992, 8, 20, 0));
		Membership newMem = ms.createMembership(newAc, newUser); // add new user to new activity
		assertNull(newMem); // creation should fail, this activity has not been allocated any sessions yet 
	}
	
	// Test MembershipService.deleteMembership()
	@Test
	public void deleteMemberships() {
		
		// Retrieve user u3 from the database
		UserService us = new UserService();
		User u3 = us.findUserByUsername("pgerardos");
		
		// Retrieve all activities from database (3 in total)
		ActivityService as = new ActivityService();
		Activity a1 = as.findActivityByName("Gymnastics"); // u3 is a member of a1 and there is a register for one of the sessions of a1 and u1
		Activity a2 = as.findActivityByName("Basketball"); // u3 is a member of a2 but there are no registers stored for a2 and u3
		Activity a3 = as.findActivityByName("Drama"); // u3 is not a member of a3
		
		// Delete u3 from activities a1, a2 and a3
		assertFalse(ms.deleteMembership(new Membership(a1, u3, null))); // cannot delete - there is a register
		assertTrue(ms.deleteMembership(new Membership(a2, u3, null))); // can delete - no register exists
		assertTrue(ms.deleteMembership(new Membership(a3, u3, null))); // successful deletion - there is no membership to delete, therefore it's considered deleted
	}
	
	// Test MembershipService.assignReport(), MembershipService.publishReport(), MembershipService.findMembership()
	@Test
	public void assignAndPublishReport() {
		
		// Get services
		UserService us = new UserService();
		ActivityService as = new ActivityService();
		
		User u3 = us.findUserByUsername("pgerardos"); // retrieve user u3
		Activity gymAc = as.findActivityByName("Gymnastics"); // retrieve activity a1
		
		Membership m = ms.findMembership(gymAc, u3); // retrieve membership of u3 in a1
		assertNotNull(m); // this is an existing membership
		assertEquals(m.getActivity(), gymAc); // for activity a1
		assertEquals(m.getUser(), u3); // and user u3
		
		// Assign report
		ms.assignReport(m, 9.5, "An excellent approach to Gymnastics");
		
		// Retrieve updated membership from database and check report content
		m = ms.findMembership(gymAc, u3);
		assertTrue((Double.toString(m.getReport().getGrade())).equals("9.5"));
		assertTrue(m.getReport().getComment().equals("An excellent approach to Gymnastics"));
		assertFalse(m.getReport().getPublished());
		
		// Publish report and re-check
		ms.publishReport(m, DateLib.getDateObject(31, 1, 2015));
		m = ms.findMembership(gymAc, u3);
		assertTrue(m.getReport().getPublished());
	}
	
	// Test MembershipService.findMembershipsByUser(), MembershipService.findMembershipsByActivity()
	@Test
	public void findMemberships() {
		
		// Get services
		UserService us = new UserService();
		ActivityService as = new ActivityService();
		
		// Retrieve all activities from database (3 in total)
		Activity a1 = as.findActivityByName("Gymnastics");
		Activity a2 = as.findActivityByName("Basketball");
		Activity a3 = as.findActivityByName("Drama");
				
		// Retrieve all users from database (3 in total)
		User u1 = us.findUserByUsername("kchristofilos");
		User u2 = us.findUserByUsername("sok");
		User u3 = us.findUserByUsername("pgerardos");
		
		assertEquals(2, ms.findMembershipsByActivity(a1).size()); // a1 has 2 members
		assertEquals(2, ms.findMembershipsByActivity(a2).size()); // a2 has 2 members
		assertEquals(0, ms.findMembershipsByActivity(a3).size()); // a3 does not have any members
		assertEquals(0, ms.findMembershipsByUser(u1).size()); // u1 is not a member in any activities
		assertEquals(2, ms.findMembershipsByUser(u2).size()); // u2 is a member in 2 activities
		assertEquals(2, ms.findMembershipsByUser(u3).size()); // u3 is a member in 2 activities
	}
}
