package controllers.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.EntityBaseTest;
import models.domain.RegistrationStatus;
import models.domain.SessionRegister;
import models.domain.User;
import models.util.DateLib;

import org.junit.Test;

public class SessionRegisterServiceTest extends EntityBaseTest
{
	private SessionRegisterService srs;

	@Override
	public void beforeTest() {
		super.beforeTest();
		srs = new SessionRegisterService();
	}
	
	// Test SessionRegister.createRegister(), SessionRegister.updateRegister(), SessionRegister.deleteRegister(), SessionRegister.findRegister()
	@Test
	public void createNewRegisters() {
		// Get services
		UserService us = new UserService();
		ActivityService as = new ActivityService();
		ActivitySessionService ass = new ActivitySessionService();
		
		// Retrieve users
		User u1 = us.findUserByUsername("kchristofilos"); // this user is a tutor
		User u2 = us.findUserByUsername("sok"); // this user is a student
		
		// Retrieve activities
		Activity gymAc = as.findActivityByName("Gymnastics"); // u2 is a member of this activity
		Activity dramaAc = as.findActivityByName("Drama"); // this activity has no members
		
		// Retrieve sessions
		ActivitySession gymSession = ass.findSessionByActivityDate(gymAc, DateLib.getDateObject(3, 3, 2015, 12, 0, 0));
		ActivitySession dramaSession = ass.findSessionByActivityDate(dramaAc, DateLib.getDateObject(2, 4, 2015));
		
		// Create registers
		assertNotNull(srs.createRegister(gymSession, u2, RegistrationStatus.Present, "good performance")); // this should be fine
		assertNull(srs.createRegister(gymSession, u1, RegistrationStatus.Present, "no comments")); // this should not work, u1 is a tutor
		assertNull(srs.createRegister(dramaSession, u2, RegistrationStatus.Present, "lousy actor")); // this should not work, u2 is not a member of this activity
		String newNote = "actually, not so good performance";
		assertNull(srs.createRegister(gymSession, u2, RegistrationStatus.Present, newNote)); // this should not work, there is already a register
		
		// Find & update register
		SessionRegister reg = srs.findRegister(gymSession, u2); // retrieve existing register from database
		reg.setNotes(newNote); // update note
		assertNotNull(srs.updateRegister(reg)); // this should work, this is an update
		reg = srs.findRegister(gymSession, u2); // retrieve updated register
		assertTrue(reg.getNotes().equals(newNote)); // verify update
		
		// Delete & re-enter register
		srs.deleteRegister(reg); // delete updated register
		assertNull(srs.findRegister(gymSession, u2)); // verify deletion
		assertNotNull(srs.createRegister(gymSession, u2, RegistrationStatus.Present, newNote + " after all")); // this should now work
		reg = srs.findRegister(gymSession, u2); // retrieve inserted register
		assertTrue(reg.getNotes().equals(newNote + " after all")); // verify insertion
	}
	
	// Test SessionRegister.findRegistersByUser(), SessionRegister.findRegistersByActivity(), SessionRegister.findRegistersByActivityUser()
	@Test
	public void findRegisters() {
		UserService us = new UserService();
		ActivityService as = new ActivityService();
		ActivitySessionService ass = new ActivitySessionService();
		
		User u1 = us.findUserByUsername("kchristofilos"); // this user is a tutor
		User u3 = us.findUserByUsername("pgerardos"); // this user is a student
		
		// Retrieve activities
		Activity basketAc = as.findActivityByName("Basketball"); // u3 is a member of this activity
		Activity dramaAc = as.findActivityByName("Drama"); // this activity has no members
		
		// Retrieve sessions
		ActivitySession basketSession = ass.findSessionByActivityDate(basketAc, DateLib.getDateObject(9, 3, 2015, 12, 0, 0));
		ActivitySession dramaSession = ass.findSessionByActivityDate(dramaAc, DateLib.getDateObject(2, 4, 2015));
		
		assertEquals(1, srs.findRegistersByUser(u3).size()); // there must be 1 register for u3
		assertEquals(0, srs.findRegistersByUser(u1).size()); // there must be no registers for u1
		assertEquals(0, srs.findRegistersBySession(basketSession).size()); // there must be no registers for this activity
		assertEquals(0, srs.findRegistersBySession(dramaSession).size()); // there must be no registers for this activity
		assertEquals(0, srs.findRegistersByActivityUser(basketAc, u3).size()); // there must be 1 register for u3 for basketball
		assertEquals(0, srs.findRegistersByActivityUser(dramaAc, u3).size()); // there must be no registers for u3 for drama
	}
}
