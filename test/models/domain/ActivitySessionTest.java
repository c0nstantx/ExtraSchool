package models.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void createNewSessions() {
		Activity a = new Activity("Basketball Club", "Can you play the game?", "O.A.K.A. Basketball Court");
		Set sessions = new HashSet<ActivitySession>();
		for (int i = 0; i < 10; i++) {
			ActivitySession newSession = (i % 2 != 0 ? new ActivitySession(DateLib.getDateObject(10 + i, 6, 2015)) : new ActivitySession(a, DateLib.getDateObject(10 + i, 7, 2015)));
			sessions.add(newSession);
		}
		a.setSessions(sessions);
		assertEquals(10, a.getSessions().size());
	}
	
	@Test
	public void addRegister() {
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
		
		// Create new session register for session as and user u
		SessionRegister sr = new SessionRegister(u, as, RegistrationStatus.AbsentWithoutPermission, "and like that, he's gone");
		as.addRegister(sr);
		assertEquals(1, as.getRegisters().size());
		
		// test toString() method
		asToString = "Session: -1, 'Basketball Club', Wed 01/04/2015 12:00, 'O.A.K.A. Basketball Court', 'Scheduled', 1 register(s)";
		assertTrue(as.toString().equals(asToString));
	}
	
	@Test
	public void printSessions() {
		Activity a = new Activity("Basketball Club", "Can you play the game?", "O.A.K.A. Basketball Court");
		ActivitySession as1 = new ActivitySession(a, DateLib.getDateObject(3, 3, 2016));
		ActivitySession as2 = new ActivitySession(a, DateLib.getDateObject(4, 3, 2016, 13, 30, 0));
		String asString1 = "Session: -1, 'Basketball Club', Thu 03/03/2016 12:00, 'O.A.K.A. Basketball Court', 'Scheduled', 0 register(s)";
		String asString2 = "Session: -1, 'Basketball Club', Fri 04/03/2016 13:30, 'O.A.K.A. Basketball Court', 'Scheduled', 0 register(s)";
		assertTrue(asString1.equals(as1.toString()));
		assertTrue(asString2.equals(as2.toString()));
	}
}
