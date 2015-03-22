package models.persistence;

import static play.test.Helpers.inMemoryDatabase;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.Option;
import static org.mockito.Mockito.*;

public class JPAUtilTest
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
	public void testConstruct()
	{
		JPAUtil jpaUtil = new JPAUtil();
		Assert.assertNotNull(jpaUtil);
		Assert.assertEquals(JPAUtil.class, jpaUtil.getClass());
	}

	@Test
	public void testCurrentEntityManager()
	{
		EntityManager em = JPAUtil.getCurrentEntityManager(true);
		Assert.assertNotNull(em);
		em = JPAUtil.getCurrentEntityManager(false);
		Assert.assertNotNull(em);
	}

	@Test
	public void testNullCurrentEntityManager()
	{
		JPAUtil.em = null;
		em = JPAUtil.getCurrentEntityManager(false);
		Assert.assertNotNull(em);
	}

	@Test
	public void testSetEntityManager()
	{
		EntityManager em = mock(EntityManager.class);
		JPAUtil.setCurrentEntityManager(em);

		Assert.assertNotNull(JPAUtil.getCurrentEntityManager());
	}
}
