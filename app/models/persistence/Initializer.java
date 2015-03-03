package models.persistence;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.SessionRegister;
import models.domain.User;
import play.db.jpa.Transactional;

/**
 * Initialize DB for testing
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
public class Initializer
{
	private EntityManager em;
	
	public Initializer()
	{
    	em = JPAUtil.getCurrentEntityManager();
    	
	}
	
    @Transactional
    public void clearDB()
    {
        Query query = em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE users");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE activities");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE activity_calendar");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE activity_absence");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE activity_students");
        query.executeUpdate();
        query = em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE");
        query.executeUpdate();
    }

    @Transactional
    public void initDB(boolean mockupData)
    {
        clearDB();
    	if (mockupData == true) {
    		populateMockupData();
    	}
    }

    @Transactional
    public void initDB()
    {
    	initDB(true);
    }
    
    private void populateMockupData()
    {
    	/* Create 10 users */
    	User u1 = new User("kchristofilos", "123456", "Konstantinos", "Christofilos", UserType.Tutor);
    	User u2 = new User("spantazaras", "123456", "Sokratis", "Pantazaras", UserType.Student);
    	User u3 = new User("pgerardos", "123456", "Pavlos", "Gerardos", UserType.Student);

    	em.persist(u1);
    	em.persist(u2);
    	em.persist(u3);
    	
    	/* Create Activities */
    	Activity a1 = new Activity("activity1", "activity1 description", new Date(), new Date());
    	Activity a2 = new Activity("activity2", "activity2 description", new Date(), new Date());

    	ActivitySession session1 = new ActivitySession(CalendarUtilities.getDate(2, 3, 2015));
    	ActivitySession session2 = new ActivitySession(CalendarUtilities.getDate(3, 3, 2015));
    	ActivitySession session3 = new ActivitySession(CalendarUtilities.getDate(9, 3, 2015));
    	ActivitySession session4 = new ActivitySession(CalendarUtilities.getDate(10, 3, 2015));
    	
    	a1.addSession(session1);
    	a2.addSession(session3);
    	a1.addSession(session2);
    	a2.addSession(session4);
    	
    	a1.addMembership(u2);
    	a1.addMembership(u3);
    	a2.addMembership(u2);
    	a2.addMembership(u3);
    	
        em.persist(a1);
        em.persist(a2);
    	
    	/* Add absences */
    	SessionRegister register1 = new SessionRegister(u2, session1, RegistrationStatus.Present, "very inattentive");
    	SessionRegister register2 = new SessionRegister(u3, session2, RegistrationStatus.AbsentDueToInjury, "injured during last training session");
    	
    	em.persist(register1);
    	em.persist(register2);
    }

}
