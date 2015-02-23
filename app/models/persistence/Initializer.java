package models.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.domain.Activity;
import models.domain.ActivityAbsence;
import models.domain.ActivityCalendar;
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
    
    @SuppressWarnings("deprecation")
	private void populateMockupData()
    {
    	/* Create 3 users */
    	User u1 = new User("kchristofilos", "123456", "Konstantinos", "Christofilos", UserType.TUTOR);
    	User u2 = new User("spantazaras", "123456", "Sokratis", "Pantazaras", UserType.STUDENT);
    	User u3 = new User("pgerardos", "123456", "Pavlos", "Gerardos", UserType.STUDENT);

    	em.persist(u1);
    	em.persist(u2);
    	em.persist(u3);
    	
    	/* Create Activities */
    	Activity a1 = new Activity("activity1", "activity1 description");
    	Activity a2 = new Activity("activity2", "activity2 description");

    	ActivityCalendar ac1 = new ActivityCalendar(new Date("2 Mar 2015"));
    	ActivityCalendar ac2 = new ActivityCalendar(new Date("3 Mar 2015"));
    	ActivityCalendar ac3 = new ActivityCalendar(new Date("9 Mar 2015"));
    	ActivityCalendar ac4 = new ActivityCalendar(new Date("10 Mar 2015"));
    	
    	a1.addActivityCalendar(ac1);
    	a2.addActivityCalendar(ac3);
    	a1.addActivityCalendar(ac2);
    	a2.addActivityCalendar(ac4);
    	
    	a1.setTutor(u1);
    	a2.setTutor(u1);
    	
    	a1.addStudent(u2);
    	a1.addStudent(u3);
    	a2.addStudent(u2);
    	a2.addStudent(u3);
    	
        em.persist(a1);
        em.persist(a2);
    	
    	/* Add absences */
    	ActivityAbsence aa1 = new ActivityAbsence(u2, ac1);
    	ActivityAbsence aa2 = new ActivityAbsence(u3, ac2);
    	
    	em.persist(aa1);
    	em.persist(aa2);
    }

}
