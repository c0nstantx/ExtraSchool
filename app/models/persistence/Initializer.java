package models.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.Membership;
import models.domain.RegistrationStatus;
import models.domain.SessionRegister;
import models.domain.User;
import models.domain.UserType;
import models.util.DateLib;
import play.db.jpa.Transactional;

/**
 * Initialize DB for testing
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 *
 */
public class Initializer {
	private EntityManager em;
	
	public Initializer() {
    	em = JPAUtil.getCurrentEntityManager();
	}
	
    @Transactional
    public void clearDB() {
        Query query = em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE users");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE activities");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE activity_sessions");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE session_registers");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE memberships");
        query.executeUpdate();
        query = em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE");
        query.executeUpdate();
    }

    @Transactional
    public void initDB(boolean mockupData) {
    	clearDB();
    	if (mockupData == true) {
    		populateMockupData();
    	}
    }

    @Transactional
    public void initDB() {
    	initDB(true);
    }
    
    /*
     * Creates a set of users, activities, memberships, sessions & registers.
     * All the entities created satisfy integrity constraints, i.e. the initial state of the database is consistent.
     * After method execution is completed, the database contains the following information:
     * 
     *  +--------------------------------------------------------------------------------------+
     *  | users                                                                                |
     *  +----+---------------+----------+-----------+--------------+--------------+------------+
     *  | id |   username    | password | user_type |  first_name  |  last_name   | birth_date |
     *  +----+---------------+----------+-----------+--------------+--------------+------------+
     *  |  1 | kchristofilos | 123456   | Tutor     | Konstantinos | Christofilos | 21.12.1985 |
     *  |  2 | sok           | 123456   | Student   | Sokratis     | Pantazaras   | 20.10.1978 |
     *  |  3 | pgerardos     | 123456   | Student   | Pavlos       | Gerardos     | 08.01.1980 |
     *  +----+---------------+----------+-----------+--------------+--------------+------------+
     *  
     *  +--------------------------------------------------------------------+
     *  | activities                                                         |
     *  +----+------------+-------------------------------+------------------+
     *  | id |    name    |          description          |      venue       |
     *  +----+------------+-------------------------------+------------------+
     *  |  1 | Gymnastics | Base course in gymnastics     | Main hall        |
     *  |  2 | Basketball | Intensive basketball training | Basketball court |
     *  |  3 | Drama      | Shakespeare plays             | Room A5          |
     *  +----+------------+-------------------------------+------------------+
     *  
     *  +-------------------------------------------------+
     *  | activity_sessions                               |
     *  +----+------------------+-----------+-------------+
     *  | id |      date        |  status   | activity_id |
     *  +----+------------------+-----------+-------------+
     *  |  1 | 02.03.2015 12.00 | Scheduled |      1      |
     *  |  2 | 03.03.2015 12.00 | Scheduled |      1      |
     *  |  3 | 09.03.2015 12.00 | Scheduled |      2      |
     *  |  4 | 10.03.2015 12.00 | Scheduled |      2      |
     *  |  5 | 05.03.2015 12.00 | Scheduled |      3      |
     *  |  6 | 10.03.2015 12.00 | Scheduled |      3      |
     *  |  7 | 12.03.2015 12.00 | Scheduled |      3      |
     *  |  8 | 17.03.2015 12.00 | Scheduled |      3      |
     *  |  9 | 19.03.2015 12.00 | Scheduled |      3      |
     *  | 10 | 24.03.2015 12.00 | Scheduled |      3      |
     *  | 11 | 26.03.2015 12.00 | Scheduled |      3      |
     *  | 12 | 31.03.2015 12.00 | Scheduled |      3      |
     *  | 13 | 02.04.2015 12.00 | Scheduled |      3      |
     *  +----+------------------+-----------+-------------+
     *  
     *  +-------------------------------------------------------------------------------------------------+
     *  | memberships                                                                                     |
     *  +----+-------------+---------+-------------------+-----------+------------------+-------+---------+
     *  | id | activity_id | user_id | registration_date | published | publication_date | grade | comment |
     *  +----+-------------+---------+-------------------+-----------+------------------+-------+---------+
     *  |  1 |      1      |    2    |    28.02.2015     |   false   |      (null)      |  0.0  |         |
     *  |  2 |      1      |    3    |    28.02.2015     |   false   |      (null)      |  0.0  |         |
     *  |  3 |      2      |    2    |    28.02.2015     |   false   |      (null)      |  0.0  |         |
     *  |  4 |      2      |    3    |    28.02.2015     |   false   |      (null)      |  0.0  |         |
     *  +----+-------------+---------+-------------------+-----------+------------------+-------+---------+
     *  
     *  +-------------------------------------------------------------------------------------------------+
     *  | session_registers                                                                               |
     *  +----+---------+---------------------+---------------------+--------------------------------------+
     *  | id | user_id | activity_session_id | registration_status |                notes                 |
     *  +----+---------+---------------------+---------------------+--------------------------------------+
     *  |  1 |    2    |          1          | Present             | very inattentive                     |
     *  |  2 |    3    |          2          | AbsentDueToInjury   | injured during last training session |
     *  +----+---------+---------------------+---------------------+--------------------------------------+
     */
    private void populateMockupData() {
    	
    	// Create & store 3 users
    	User u1 = new User("kchristofilos", "123456", UserType.Tutor, "Konstantinos", "Christofilos", DateLib.getDateObject(21, 12, 1985));
    	User u2 = new User("sok", "123456", UserType.Student, "Sokratis", "Pantazaras", DateLib.getDateObject(20, 10, 1978));
    	User u3 = new User("pgerardos", "123456", UserType.Student, "Pavlos", "Gerardos", DateLib.getDateObject(8, 1, 1980));
    	User u4 = new User("admin", "123456", UserType.Admin, "Super", "Admin", DateLib.getDateObject(1, 1, 2000));
    	em.persist(u1);
    	em.persist(u2);
    	em.persist(u3);
    	em.persist(u4);
    	
    	// Create 3 activities
    	Activity a1 = new Activity("Gymnastics", "Base course in gymnastics", "Main hall");
    	Activity a2 = new Activity("Basketball", "Intensive basketball training", "Basketball court");
    	Activity a3 = new Activity("Drama", "Shakespeare plays", "Room A5");

    	// Create 4 sessions
    	ActivitySession session1 = new ActivitySession(DateLib.getDateObject(2, 3, 2015));
    	ActivitySession session2 = new ActivitySession(DateLib.getDateObject(3, 3, 2015));
    	ActivitySession session3 = new ActivitySession(DateLib.getDateObject(9, 3, 2015));
    	ActivitySession session4 = new ActivitySession(DateLib.getDateObject(10, 3, 2015));
    	
    	// Assign sessions to activities
    	a1.addSession(session1);
    	a2.addSession(session3);
    	a1.addSession(session2);
    	a2.addSession(session4);
    	
    	/* Create sessions for activity 3 on the following dates:
    	 * 05/03 (Thu), 10/03 (Tue), 12/03 (Thu), 17/03 (Tue), 19/03 (Thu),
    	 * 24/03 (Tue), 26/03 (Thu), 31/03 (Tue), 02/04 (Thu)
    	 */
    	a3.createSessions(DateLib.getDateObject(4, 3, 2015), DateLib.getDateObject(4, 4, 2015), new boolean[] {false, false, true, false, true, false, false});
    	
    	// Create 4 memberships and link to activities & users
    	Membership m1 = new Membership(a1, u2, DateLib.getDateObject());
    	Membership m2 = new Membership(a1, u3, DateLib.getDateObject());
    	Membership m3 = new Membership(a2, u2, DateLib.getDateObject());
    	Membership m4 = new Membership(a2, u3, DateLib.getDateObject());
    	a1.addMembership(m1);
    	a1.addMembership(m2);
    	a2.addMembership(m3);
    	a2.addMembership(m4);
    	u2.addMembership(m1);
    	u2.addMembership(m3);
    	u3.addMembership(m2);
    	u3.addMembership(m4);
    	
    	// Store activities
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        
        // Create 2 registers and link to sessions & users
        SessionRegister reg1 = new SessionRegister(u2, session1, RegistrationStatus.Present, "very inattentive");
        SessionRegister reg2 = new SessionRegister(u3, session2, RegistrationStatus.AbsentDueToInjury, "injured during last training session");
        session1.addRegister(reg1);
        session2.addRegister(reg2);
        u2.addRegister(reg1);
        u3.addRegister(reg2);
        
        // Store registers
    	em.persist(reg1);
    	em.persist(reg2);
    }
}
