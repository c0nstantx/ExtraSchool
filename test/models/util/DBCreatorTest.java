package models.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.persistence.Query;

import models.domain.Activity;
import models.domain.EntityBaseTest;
import models.domain.User;
import models.domain.UserType;

import org.junit.Before;
import org.junit.Test;

import play.db.jpa.Transactional;
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
    	// TEST ATHLETICS
    	String athleticsDescription = "Athletics is an exclusive collection of sporting events that involve competitive running, jumping, "
				+ "throwing, and walking. The most common types of athletics competitions are track and field, road running, cross country running, "
				+ "and race walking.";
    	testActivity("Athletics", athleticsDescription, "Outside fields", 40, "kvoisey");
    	
    	// TEST BASKETBALL
    	String basketballDescription = "Basketball is a sport played by two teams of five players on a rectangular court. The objective is "
				+ "to shoot a ball through a hoop 18 inches (46 cm) in diameter and 10 feet (3.048 m) high mounted to a backboard at each end.";
    	testActivity("Basketball", basketballDescription, "Basketball court", 60, "clatanis");
    	
    	// TEST DRAMA CLUB
    	String dramaDescription = "Drama is the specific mode of fiction represented in performance.The term comes from a Greek word meaning action, "
				+ "which is derived from the verb meaning to do or to act.";
    	testActivity("Drama Club", dramaDescription, "Room A5", 40, "jgunn");
    	
    	// TEST FOOTBALL
    	String footballDescription = "Association football, more commonly known as football or soccer, is a sport played between two teams of eleven "
				+ "players with a spherical ball. It is played by 250 million players in over 200 countries, making it the world's most popular sport.";
    	testActivity("Football", footballDescription, "Football pitch", 60, "nmoustakas");
    	
    	// TEST GYMNASTICS
    	String gymnasticsDescription = "Gymnastics is a sport involving the performance of exercises requiring physical strength, flexibility, "
				+ "power, agility, coordination, grace, balance and control. Internationally, all of the competitive gymnastics events are governed "
				+ "by the FIG.";
    	testActivity("Gymnastics", gymnasticsDescription, "Inside hall", 60, "gziara");
    	
    	// TEST HISTORY CLUB
    	String historyDescription = "History is the study of the past, particularly how it relates to humans.It is an umbrella term that relates to "
				+ "past events as well as the memory, discovery, collection, organization, presentation, and interpretation of information about these "
				+ "events.";
    	testActivity("History Club", historyDescription, "Room B1", 40, "lstewart");
    	
    	// TEST ICT CLUB
    	String ictDescription = "Information and communications technology (ICT) is often used as an extended synonym for information technology (IT), "
				+ "but is a more specific term that stresses the role of unified communication and the integration of telecommunications and computers.";
    	testActivity("ICT Club", ictDescription, "ICT lab 1", 40, "troberts");
    	
    	// TEST SWIMMING
    	String swimmingDescription = "The recreational activity of swimming has been recorded since prehistoric times. The earliest recording of "
				+ "swimming dates back to Stone Age paintings from around 10000 years ago. Written references date from 2000 BC.";
    	testActivity("Swimming", swimmingDescription, "Swimming pool", 60, "lpearson");
    	
    	// MARTIAL ARTS
    	String martialArtsDescription = "Martial arts are codified systems and traditions of combat practices, which are practiced for a variety "
				+ "of reasons: self-defense, competition, physical health and fitness, entertainment, as well as mental, physical, and spiritual development.";
    	testActivity("Martial Arts", martialArtsDescription, "Inside hall", 20, "rwatts");
    	
    	// TRAMPOLINE
    	String trampolineDescription = "A trampoline is a device consisting of a piece of taut, strong fabric stretched over a steel frame using many coiled "
				+ "springs. People bounce on trampolines for recreational and competitive purposes.";
    	testActivity("Trampoline", trampolineDescription, "Inside hall", 20, "pkolyvas");
    	
    	// VOLLEYBALL
    	String volleyballDescription = "Volleyball is a team sport in which two teams of six players are separated by a net. Each team tries to "
				+ "score points by grounding a ball on the other team's court under organized rules.";
    	testActivity("Volleyball", volleyballDescription, "Basketball court", 40, "dgrannon");
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
