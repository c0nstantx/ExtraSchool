package models.domain;

import javax.persistence.EntityManager;

import models.persistence.JPAUtil;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest
{

	protected EntityManager em;
	
	@BeforeClass
	public static void initTest()
	{
		System.out.println("Starting unit test...");
	}
	
	@Before
	public void setUp()
	{
		em = JPAUtil.getCurrentEntityManager();
	}
	
	@Test
	public void createNewUser()
	{
		User user = new User();
		user.setUsername("kostasx");
		user.setPassword("123456");
		em.persist(user);
	}

}
