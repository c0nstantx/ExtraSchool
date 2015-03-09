package models.domain;

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
public class SessionRegisterTest extends EntityBaseTest {
	
	@Test
	public void createNewRegisters() {
		
		// Create necessary objects (student, activity, session)
		User u = new User("kchristofilos1", "123456", UserType.Student, "Konstantinos", "Christofilos", DateLib.getDateObject(21, 12, 1985));
		Activity a = new Activity("Basketball Club", "Can you play the game?", "O.A.K.A. Basketball Court");
		ActivitySession as = new ActivitySession(a, DateLib.getDateObject(1, 4, 2015));
		
		// Store objects
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		em.persist(a);
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		em.persist(as);
		em.getTransaction().commit();
		
		SessionRegister reg1 = new SessionRegister(u, as, RegistrationStatus.Present, "poor performance");
		
		assertTrue(reg1.getStudent().getUsername().equals("kchristofilos1"));
		assertTrue(reg1.getSession().getActivity().getVenue().equals("O.A.K.A. Basketball Court"));
		assertTrue(reg1.getStatus() == RegistrationStatus.Present);
		assertTrue(reg1.getNotes().equals("poor performance"));
		
		String prefix = "Register: -1";
		assertTrue(reg1.toString().startsWith(prefix));
		
		u.addRegister(reg1);
		as.addRegister(reg1);

		em.getTransaction().begin();
		em.persist(reg1);
		em.getTransaction().commit();
		
		reg1.setStatus(RegistrationStatus.AbsentDueToInjury);
		reg1.setNotes("He was absent after all. Now who was I thinking of?");
		reg1.setStudent(u);
		reg1.setSession(as);
		
		prefix = "Register: " + reg1.getId();
		assertTrue(reg1.toString().startsWith(prefix));
	}
}
