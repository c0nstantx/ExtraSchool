package models.util.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import models.domain.Activity;
import models.domain.EntityBaseTest;
import models.domain.Membership;
import models.domain.SessionRegister;
import models.domain.User;
import models.domain.UserType;
import models.util.DateLib;

import org.junit.Before;
import org.junit.Test;

import controllers.service.ActivityService;
import controllers.service.MembershipService;
import controllers.service.SessionRegisterService;
import controllers.service.UserService;

/**
 * DBCreatorTest class for testing DBCreator
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class DBCreatorTest extends EntityBaseTest {

	@Override
	@Before
    public void beforeTest()
    {
    	em.getTransaction().begin();
    	DBCreator dbCreator = new DBCreator();
    	dbCreator.clearDB();
    	dbCreator.preparePresentationDatabase();
    	em.getTransaction().commit();
    }

    @Test
    public void testAdmins() {
    	UserService us = new UserService();
    	User kchristof = us.findUserByUsername("kchristof");
    	User pgerard = us.findUserByUsername("pgerard");
    	User spanta = us.findUserByUsername("spanta");
    	assertTrue(kchristof.getFirstName().equals("Konstantinos"));
    	assertTrue(pgerard.getLastName().equals("Gerardos"));
    	assertEquals(spanta.getUserType(), UserType.Admin);
    }
    
    @Test
    public void testPupils() {
    	for (int i = 0; i < PupilBank.pupilData.length; i++) {
    		testPupil(PupilBank.pupilData[i].getUserame(),
    				PupilBank.pupilData[i].getPassword(),
    				PupilBank.pupilData[i].getFirstName(),
    				PupilBank.pupilData[i].getLastName(),
    				PupilBank.pupilData[i].getBirthDate());
    	}
    }
    
    @Test
    public void testActivitiesAndTutors() {
    	Iterator<Entry<String, ActivityInfo>> it = ActivityBank.ActivityInfoMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, ActivityInfo> entry = (Entry<String, ActivityInfo>)it.next();
			ActivityInfo activityInfo = entry.getValue();
			testActivity(activityInfo.getName(), activityInfo.getDescription(), activityInfo.getVenue(), activityInfo.getTotalNumberOfSessions(), activityInfo.getTutorUsername());
		}
    }
    
    @Test
    public void testSessionRegisters() {
    	try {
			PrintStream ps = new PrintStream("registers.txt");
			SessionRegisterService srs = new SessionRegisterService();
	    	List<SessionRegister> list = srs.findAllRegisters();
	    	Iterator<SessionRegister> it = list.iterator();
	    	while (it.hasNext()) {
	    		SessionRegister reg = (SessionRegister)it.next();
	    		String regs = "Register for " + reg.getStudent() + ", " + reg.getSession();
	    		ps.println(regs);
	    	}
			ps.close();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
    }
    
    @Test
    public void testMemberships() {
    	try {
			PrintStream ps = new PrintStream("memberships.txt");
			MembershipService ms = new MembershipService();
	    	List<Membership> list = ms.findAllMemberships();
	    	Iterator<Membership> it = list.iterator();
	    	while (it.hasNext()) {
	    		Membership mem = (Membership)it.next();
	    		String mems = "Membership for " + mem.getUser() + ", " + mem.getActivity();
	    		ps.println(mems);
	    	}
			ps.close();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
    }
    
    private void testPupil(String username, String password, String firstName, String lastName, Date birthDate) {
    	UserService us = new UserService();
    	User pupil = us.findUserByUsername(username);
    	assertTrue(username.equals(pupil.getUsername()));
    	assertTrue(password.equals(pupil.getPassword()));
    	assertEquals(UserType.Student, pupil.getUserType());
    	assertTrue(firstName.equals(pupil.getFirstName()));
    	assertTrue(lastName.equals(pupil.getLastName()));
    	assertTrue(DateLib.equals(birthDate, pupil.getBirthDate()));
    }
    
    private void testActivity(String name, String description, String venue, int numberOfSessions, String tutorUsername) {
    	ActivityService as = new ActivityService();
    	Activity activity = as.findActivityByName(name);
    	assertTrue(description.equals(activity.getDescription()));
    	assertTrue(venue.equals(activity.getVenue()));
    	assertEquals(numberOfSessions, activity.getSessions().size());
    	User tutor = as.findActivityTutor(name);
    	assertTrue(tutorUsername.equals(tutor.getUsername()));
    }
}
