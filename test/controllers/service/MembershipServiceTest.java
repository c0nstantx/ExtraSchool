package controllers.service;

import static org.junit.Assert.assertEquals;
import models.domain.Activity;
import models.domain.EntityBaseTest;
import models.domain.User;

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
		//User u2 = us.findUserByUsername("sok");
		//User u3 = us.findUserByUsername("pgerardos");
		
		// Enroll user u1 in all activities
		ms.createMembership(a1, u1); // this is possible
		ms.createMembership(a2, u1); // this is possible
		ms.createMembership(a3, u1); // this is not possible (clash on 10/03/2015 12:00)
		
		// Verify that u1 was only registered for 2 activities
		assertEquals(2, u1.getMemberships().size());
	}
}
