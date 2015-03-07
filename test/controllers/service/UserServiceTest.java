package controllers.service;

import java.util.ArrayList;
import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	@Test
	public void addNewUsers() {
		/* Initial users are 3 */
		List<User> users = new ArrayList<User>();
		
		us.createUser("kchris", "123456", UserType.Tutor, "Konstantinos", "Christofilos", DateLib.getDateObject(21, 12, 1985));
		us.createUser("sokratis", "123456", UserType.Tutor, "Sokratis", "Alafouzos", DateLib.getDateObject(21, 12, 1985));
		
		users = em.createQuery("SELECT u FROM User u").getResultList();
		
		Assert.assertEquals(5, users.size());
	}
	
	@Test
	public void addNewExistingUsers() {
		/* Initial users are 3 */
		
		us.createUser("kchristofilos", "123456", UserType.Tutor, "Konstantinos", "Christofilos", DateLib.getDateObject(21, 12, 1985));
		us.createUser("sok", "123456", UserType.Tutor, "Sokratis", "Alafouzos", DateLib.getDateObject(21, 12, 1985));
		
		@SuppressWarnings("unchecked")
		List<User> users = em.createQuery("SELECT u FROM User u").getResultList();
		
		Assert.assertEquals(3, users.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void deleteUser() {
		/* Initial users are 3 */
		List<User> users = new ArrayList<User>();
		
		User user = (User) em.createQuery("SELECT u FROM User u WHERE username = :username")
				.setParameter("username", "kchristofilos")
				.getSingleResult();
		us.deleteUser(user);
		
		users = em.createQuery("SELECT u FROM User u").getResultList();
		
		Assert.assertEquals(2, users.size());
	}

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

		Assert.assertEquals("Kostas", retrievedUser.getPerson().getFirstName());
	}

//	@Test
//	public void updateUserWithExistingUsername() {
//		User user = (User) em.createQuery("SELECT u FROM User u WHERE username = :username")
//				.setParameter("username", "kchristofilos")
//				.getSingleResult();
//		user.setUsername("sok");
//		List<User> users = em.createQuery("SELECT u FROM User u WHERE username = :username")
//				.setParameter("username", "sok")
//				.getResultList();
//
//		for (User user2 : users) {
//			System.out.println(user2.toString());
//		}
//		us.updateUser(user);
//	}
}
