package models.domain;

import static play.test.Helpers.inMemoryDatabase;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
    	FakeApplication app = Helpers.fakeApplication(inMemoryDatabase());
		Helpers.start(app);
		Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
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
    	Initializer init = new Initializer();
    	init.initDB();
    	em.getTransaction().commit();
    }
    
    @After
    public void afterTest()
    {
    	em.clear();
    }
}
