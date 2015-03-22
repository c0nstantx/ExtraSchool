package models.persistence;

import static play.test.Helpers.inMemoryDatabase;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.Option;

public class InitializerTest
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
    
	@Test
	public void testContstructorWithBoolean()
	{
    	em.getTransaction().begin();
    	Initializer init = new Initializer();
    	init.initDB(true);
    	em.getTransaction().commit();
    	em.getTransaction().begin();
    	init.initDB(false);
    	em.getTransaction().commit();
	}
}
