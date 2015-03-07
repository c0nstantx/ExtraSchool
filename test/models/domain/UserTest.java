package models.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import models.persistence.UserType;
import models.util.DateLib;

import org.junit.Assert;
import org.junit.Test;

import controllers.service.ActivityService;
import controllers.service.UserService;

/**
 * 
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 *
 */
public class UserTest extends EntityBaseTest
{
	@Test
	public void countStartupUsers()
	{
		@SuppressWarnings("unchecked")
		List<User> users = em.createQuery("SELECT user FROM User user").getResultList();
		Assert.assertEquals(3, users.size());
	}
	
	@Test
	public void createNewUser()
	{
		/* Create user */
		User user = new User();
		user.setUsername("kostasx");
		user.setPassword("123456");
		user.setUserType(UserType.Tutor);
		Person person = new Person();
		person.setFirstName("Konstantinos");
		person.setLastName("Christofilos");
		person.setBirthDate(DateLib.getDateObject(21, 12, 1985));
		user.setPerson(person);
		em.persist(user);
		
		/* Check if user is saved */
        Query query = em.createQuery("SELECT user FROM User user WHERE username = :uname");
        query.setParameter("uname", "kostasx");
		User retrievedUser = (User) query.getSingleResult();
        Assert.assertEquals(user, retrievedUser);
	}
	
	@Test
	public void createNewUserFromConstructor()
	{
		/* Create user */
		User user = new User("kostasx", "123456", UserType.Tutor, "Konstantinos", "Christofilos", DateLib.getDateObject(21, 12, 1985));
		em.persist(user);
		
		/* Check if user is saved */
        Query query = em.createQuery("SELECT user FROM User user WHERE username = :uname");
        query.setParameter("uname", "kostasx");
		User retrievedUser = (User) query.getSingleResult();
        Assert.assertEquals(user, retrievedUser);
	}
	
	@Test
	public void updateUser()
	{
		/* Create new user */
		createNewUser();
		
		/* Update user */
        Query query = em.createQuery("SELECT user FROM User user WHERE username = :uname");
        query.setParameter("uname", "kostasx");
        @SuppressWarnings("unchecked")
		List<User> results = query.getResultList();
        User user = results.get(0);
        user.setUsername("user1");
        em.merge(user);
        
        /* Check if user is updated */
        query = em.createQuery("SELECT user FROM User user WHERE username = :uname");
        query.setParameter("uname", "user1");
        @SuppressWarnings("unchecked")
		List<User> updatedResults = query.getResultList();
        User retrievedUser = updatedResults.get(0);
        Assert.assertEquals(retrievedUser.getUsername(), "user1");
	}
	
	@Test
	public void userStringify()
	{
		UserService us = new UserService();
		List<User> users = us.getUsers();
		String userString = "";
		
		for (User user : users) {
			userString = "User: '"+user.getId()+"', '"+user.getUsername()+"', '"+user.getPassword()+"', '"
		+user.getUserType().toString()+"', '"+user.getPerson().getFirstName()+"', '"+user.getPerson().getLastName()
		+"', "+DateLib.dateAsString(user.getPerson().getBirthDate());
			Assert.assertEquals(userString, user.toString());
		}
	}

	@Test
	public void userAddMemberships()
	{
		UserService us = new UserService();
		ActivityService as = new ActivityService();
		
		User user = us.findUserByUsername("sok");
		Activity activity = as.findByName("activity1");
		
		user.addMembership(activity);
		
		em.persist(user);
		
		Assert.assertEquals(1, user.getMemberships().size());
	}

	@Test
	public void userSetMemberships()
	{
		UserService us = new UserService();
		ActivityService as = new ActivityService();
		
		User user = us.findUserByUsername("sok");
		List<Activity> activities = as.findAll();
		Set<Membership> memberships = new HashSet<>();
		for (Activity activity : activities) {
			Membership membership = new Membership();
			membership.setActivity(activity);
			membership.setRegistrationDate(new Date());
			memberships.add(membership);
		}
		user.setMemberships(memberships);
		
		em.persist(user);
		
		Assert.assertEquals(3, user.getMemberships().size());
	}

	@Test
	public void userEquals()
	{
		UserService us = new UserService();
		User user1 = us.findUserByUsername("sok");
		User user2 = us.findUserByUsername("pgerardos");
		
		Assert.assertFalse(user1.equals(user2));
		Assert.assertTrue(user1.equals(user1));
		Assert.assertFalse(user2.equals(null));
	}
}
