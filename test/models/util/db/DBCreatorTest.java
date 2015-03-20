package models.util.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Map.Entry;

import models.domain.Activity;
import models.domain.EntityBaseTest;
import models.domain.User;
import models.domain.UserType;

import org.junit.Before;
import org.junit.Test;

import controllers.service.ActivityService;
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
    public void testUsers() {
    	UserService us = new UserService();
    	User kchristof = us.findUserByUsername("kchristof");
    	User pgerard = us.findUserByUsername("pgerard");
    	User spanta = us.findUserByUsername("spanta");
    	assertTrue(kchristof.getFirstName().equals("Konstantinos"));
    	assertTrue(pgerard.getLastName().equals("Gerardos"));
    	assertEquals(spanta.getUserType(), UserType.Admin);
    }
    
    @Test
    public void testActivities() {
    	Iterator<Entry<String, ActivityInfo>> it = ActivityBank.ActivityInfoMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, ActivityInfo> entry = (Entry<String, ActivityInfo>)it.next();
			ActivityInfo activityInfo = entry.getValue();
			testActivity(activityInfo.getName(), activityInfo.getDescription(), activityInfo.getVenue(), activityInfo.getTotalNumberOfSessions(), activityInfo.getTutorUsername());
		}
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
