package models.domain;

import java.util.List;

import javax.persistence.Query;

import models.persistence.UserType;
import models.util.DateLib;

import org.junit.Assert;
import org.junit.Test;

import controllers.service.UserService;

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

}
