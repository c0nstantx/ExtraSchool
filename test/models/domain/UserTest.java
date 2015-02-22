package models.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.persistence.JPAUtil;

import org.junit.Assert;
import org.junit.Test;

public class UserTest extends EntityBaseTest
{
	
	@Test
	public void createNewUser()
	{
		User user = new User();
		user.setUsername("kostasx");
		user.setPassword("123456");
		em.persist(user);
		
        Query query = em.createQuery("SELECT user FROM User user WHERE username = :uname");
        query.setParameter("uname", "kostasx");
        @SuppressWarnings("unchecked")
		List<User> results = query.getResultList();
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(user, results.get(0));
	}
	
	@Test
	public void updateUser()
	{
		createNewUser();
		
        Query query = em.createQuery("SELECT user FROM User user WHERE username = :uname");
        query.setParameter("uname", "kostasx");
        @SuppressWarnings("unchecked")
		List<User> results = query.getResultList();
        User user = results.get(0);
        user.setUsername("user1");
        em.merge(user);
        
        query = em.createQuery("SELECT user FROM User user WHERE username = :uname");
        query.setParameter("uname", "user1");
        @SuppressWarnings("unchecked")
		List<User> updatedResults = query.getResultList();
        User retrievedUser = updatedResults.get(0);
        Assert.assertEquals(retrievedUser.getUsername(), "user1");
	}
}
