package models.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import models.persistence.RegistrationStatus;
import models.persistence.UserType;
import models.util.DateLib;

import org.junit.Test;

/**
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class ActivitySessionTest extends EntityBaseTest {
	@Test
	public void testActivitySession() {
		// Create parent activity
		Activity a = new Activity("Basketball Club", "Can you play the game?", "O.A.K.A. Basketball Court");
		
		// Create activity session and link to parent activity
		ActivitySession as = new ActivitySession(a, DateLib.getDateObject(1, 4, 2015));
		as.setActivity(a);
		
		// Create activity session string representation manually and test toString() method
		String asToString = "Session: -1, 'Basketball Club', Wed 01/04/2015 12:00, 'O.A.K.A. Basketball Court', 'Scheduled', 0 register(s)";
		assertTrue(as.toString().equals(asToString));
		
		// Create user
		User u = new User("kchristofilos", "123456", UserType.Tutor, "Konstantinos", "Christofilos", DateLib.getDateObject(21, 12, 1985));
		
		// Create new session register for sessuib as and user u
		SessionRegister sr = new SessionRegister(u, as, RegistrationStatus.AbsentWithoutPermission, "and like that, he's gone");
		as.addRegister(sr);
		assertEquals(1, as.getRegisters().size());
		
		// test toString() method
		asToString = "Session: -1, 'Basketball Club', Wed 01/04/2015 12:00, 'O.A.K.A. Basketball Court', 'Scheduled', 1 register(s)";
		assertTrue(as.toString().equals(asToString));
	}
}
