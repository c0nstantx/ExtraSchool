package models.util.db;

import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.Membership;
import models.domain.SessionRegister;
import models.domain.User;
import models.domain.UserType;
import models.persistence.JPAUtil;
import models.util.DateLib;
import play.db.jpa.Transactional;

/**
 * DBCreator Class
 * Populates the database with data
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class DBCreator {
	
	public static final Date CurrentDate = DateLib.getDateObject(23, 3, 2015);
	
	private EntityManager em;
	
	public DBCreator() {
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
	
	public void preparePresentationDatabase() {
		createAdmins();
		setupActivities();
	}
	
	private void createAdmins() {
		User admin1 = new User("kchristof", "123456", UserType.Admin, "Konstantinos", "Christofilos", DateLib.getDateObject(17, 3, 1981));
		em.persist(admin1);
		User admin2 = new User("pgerard", "123456", UserType.Admin, "Pavlos", "Gerardos", DateLib.getDateObject(14, 3, 1979));
		em.persist(admin2);
		User admin3 = new User("spanta", "123456", UserType.Admin, "Sokratis", "Pantazaras", DateLib.getDateObject(20, 10, 1978));
		em.persist(admin3);
		User testAdmin = new User("admin", "admin", UserType.Admin, "Test", "Admin", DateLib.getDateObject(1, 1, 1980));
		em.persist(testAdmin);
	}
	
	private void setupActivities() {
		User[] pupils = createPupils();
		Iterator<Entry<String, ActivityInfo>> it = ActivityBank.ActivityInfoMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, ActivityInfo> entry = (Entry<String, ActivityInfo>)it.next();
			ActivityInfo activityInfo = entry.getValue();
			Membership[] activityMemberships = activityInfo.setupActivity(pupils, DateLib.getDateObject(1, 1, 2015));
			Activity activity = activityMemberships[0].getActivity();
			em.persist(activity);
			persistObjects(activityMemberships);
			createSessionRegisters(activity, activityMemberships);
		}
	}
	
	private void createSessionRegisters(Activity activity, Membership[] activityMemberships) {
		for (int i = 1; i < activityMemberships.length; i++) {
			Iterator<ActivitySession> it = activity.getSessions().iterator();
			while (it.hasNext()) {
				ActivitySession session = (ActivitySession)it.next();
				if (DateLib.preceeds(session.getDate(), CurrentDate)) {
					User pupil = activityMemberships[i].getUser();
					SessionRegister register = SessionRegisterBank.createSessionRegister(pupil, session);
					em.persist(register);
					em.merge(pupil);
					em.merge(session);
				}
			}
		}
		
		
//		System.out.println(activity);
//		System.out.println("Members: " + activity.getMemberships().size());
		Iterator<ActivitySession> it = activity.getSessions().iterator();
		while (it.hasNext()) {
			ActivitySession session = (ActivitySession)it.next();
//			System.out.println(session);
		}
//		System.out.println("--------------------");
	}
	
	private User[] createPupils() {
		User[] pupils = new User[PupilBank.pupilData.length];
		for (int i = 0; i < PupilBank.pupilData.length; i++) {
			pupils[i] = PupilBank.pupilData[i].createPupil();
			em.persist(pupils[i]);
		}
		return pupils;
	}
	
	private void persistObjects(Membership[] activityMemberships) {
		for (int i = 0; i < activityMemberships.length; i++) {
			User user = activityMemberships[i].getUser();
			em.merge(user);
			em.persist(activityMemberships[i]);
		}
	}
}