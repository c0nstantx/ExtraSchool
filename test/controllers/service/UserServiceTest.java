package controllers.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import models.domain.Activity;
import models.domain.EntityBaseTest;
import models.util.DateLib;
import models.persistence.UserType;

import org.junit.Assert;
import org.junit.Test;

import models.domain.User;

public class UserServiceTest extends EntityBaseTest
{
	private UserService us;

	@Override
	public void beforeTest() {
		super.beforeTest();
		us = new UserService();
	}
	
	// Testing UserService.createUser()
	@SuppressWarnings("unchecked")
	@Test
	public void addNewUsers() {
		/* Initial users are 3 */
		List<User> users = new ArrayList<User>();
		
		us.createUser("kchris", "123456", UserType.Tutor, "Konstantinos", "Christofilos", DateLib.getDateObject(21, 12, 1985));
		us.createUser("sokratis", "123456", UserType.Tutor, "Sokratis", "Alafouzos", DateLib.getDateObject(21, 12, 1985));
		
		users = em.createQuery("SELECT u FROM User u").getResultList();
		
		assertEquals(5, users.size());
	}
	
	// Testing UserService.createUser()
	@Test
	public void addNewExistingUsers() {
		/* Initial users are 3 */
		
		us.createUser("kchristofilos", "123456", UserType.Tutor, "Konstantinos", "Christofilos", DateLib.getDateObject(21, 12, 1985));
		us.createUser("sok", "123456", UserType.Tutor, "Sokratis", "Alafouzos", DateLib.getDateObject(21, 12, 1985));
		
		@SuppressWarnings("unchecked")
		List<User> users = em.createQuery("SELECT u FROM User u").getResultList();
		
		assertEquals(3, users.size());
	}
	
	// Testing UserService.updateUser()
	@Test
	public void updateUser() {
		User user = (User) em.createQuery("SELECT u FROM User u WHERE username = :username")
				.setParameter("username", "kchristofilos")
				.getSingleResult();
		user.getPerson().setFirstName("Kostas");
		us.updateUser(user);
		
		User retrievedUser = (User) em.createQuery("SELECT u FROM User u WHERE username = :username")
				.setParameter("username", "kchristofilos")
				.getSingleResult();

		assertEquals("Kostas", retrievedUser.getPerson().getFirstName());
	}

	
	// Testing UserService.deleteUser()
	@Test
	public void deleteUser() {
		User user = (User) em.createQuery("SELECT u FROM User u WHERE username = :username")
				.setParameter("username", "kchristofilos")
				.getSingleResult();
		us.deleteUser(user);
		
		@SuppressWarnings("unchecked")
		List<User> users = em.createQuery("SELECT u FROM User u").getResultList();
		
		assertEquals(2, users.size()); // initial users are 3, should now be 2
		
		/* Get first user and alter username */
		user = users.get(0);
		user.setUsername("AnotherUsername");
		boolean result = us.deleteUser(user);
		assertFalse(result);
	}

	// Testing UserService.findAllUsers(), UserService.findUserByUsername()
	@Test
	public void findUsers() {
		List<User> users = us.findAllUsers(); // initial users are 3
		Assert.assertEquals(3, users.size()); // verify
		User u1 = us.findUserByUsername("kchristofilos"); // retrieve user u1
		assertNotNull(u1); // verify retrieval
		assertTrue(u1.getPerson().getFirstName().equals("Konstantinos")); // verify first name
		User u2 = us.findUserByUsername("sok"); // retrieve user u2
		assertNotNull(u2); // verify retrieval
		assertTrue(u2.getPerson().getFirstName().equals("Sokratis")); // verify first name
		User u3 = us.findUserByUsername("pgerardos"); // retrieve user u2
		assertNotNull(u3); // verify retrieval
		assertTrue(u3.getPerson().getFirstName().equals("Pavlos")); // verify first name
	}
	
	// Testing UserService.isRegisteredFor()
	@Test
	public void usersRegisteredForActivities() {
		ActivityService as = new ActivityService();
		Activity a1 = as.findActivityByName("Gymnastics");
		Activity a2 = as.findActivityByName("Basketball");
		Activity a3 = as.findActivityByName("Drama");
		User u = us.findUserByUsername("pgerardos"); // retrieve user u3, who is registered for Gymnastics and Basketball, but not for Drama
		assertTrue(us.isRegisteredFor(a1, u));
		assertTrue(us.isRegisteredFor(a2, u));
		assertFalse(us.isRegisteredFor(a3, u));
	}
}
