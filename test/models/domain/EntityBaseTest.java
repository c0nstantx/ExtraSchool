package models.domain;

import static play.test.Helpers.inMemoryDatabase;

import javax.persistence.EntityManager;

import models.persistence.Initializer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.Option;

/**
 * Base class for Entity testing
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
abstract public class EntityBaseTest
{

	protected static EntityManager em;

    @BeforeClass
    public static void setUp()
    {
		System.out.println("Starting Unit Test...");
    	FakeApplication app = Helpers.fakeApplication(inMemoryDatabase());
		Helpers.start(app);
		Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(
				JPAPlugin.class);
		em = jpaPlugin.get().em("default");
		JPA.bindForCurrentThread(em);
    }
    
    @AfterClass
    public static void tearDown()
    {
    	JPA.bindForCurrentThread(null);
		em.close();
    }
  
    @Before
    public void beforeTest()
    {
    	em.getTransaction().begin();
    	Initializer init = new Initializer(em);
    	init.initDB();
    }
    
    @After
    public void afterTest()
    {
    	em.getTransaction().commit();
    	em.clear();
    }
	
}
