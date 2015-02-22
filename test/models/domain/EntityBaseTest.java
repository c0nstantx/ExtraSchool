package models.domain;

import javax.persistence.EntityManager;

import models.persistence.JPAUtil;

import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Base class for Entity testing
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
abstract public class EntityBaseTest {

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
	
}
