package models.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import controllers.service.ActivityService;
import controllers.service.UserService;
import models.util.DateLib;

/**
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class ActivityTest extends EntityBaseTest
{

	@Test
	public void createNewActivity()
	{
		Activity activity = new Activity();
		activity.setName("Activity Test");
		activity.setDescription("Test activity description");
		activity.setVenue("School's yard");

		Assert.assertEquals("Activity Test", activity.getName());
		Assert.assertEquals("Test activity description", activity.getDescription());
		Assert.assertEquals("School's yard", activity.getVenue());
	}

	@Test
	public void addMemberships()
	{
		ActivityService as = new ActivityService();
		Activity activity = as.findActivityByName("Gymnastics");
		UserService us = new UserService();
		List<User> users = us.findAllUsers();
		Set<Membership> studentMembers = new HashSet<>();
		User tutor = new User();
		for (User user : users) {
			if (user.getUserType() == UserType.Tutor) {
				tutor = user;
			} else if (user.getUserType() == UserType.Student) {
				Membership membership = new Membership();
				membership.setUser(user);
				studentMembers.add(membership);
			}
		}
		activity.setMemberships(studentMembers);
		Membership m = new Membership(activity, tutor, DateLib.getDateObject());
		activity.addMembership(m);
		
		Assert.assertEquals(3, activity.getMemberships().size());
	}

	@Test
	public void addSessions()
	{
		ActivityService as = new ActivityService();
		Activity activity = as.findActivityByName("Gymnastics");

		Set<ActivitySession> sessions = new HashSet<>();
		ActivitySession as1 = new ActivitySession();
		as1.setDate(DateLib.getDateObject(5, 4, 2015));
		ActivitySession as2 = new ActivitySession();
		as2.setDate(DateLib.getDateObject(6, 4, 2015));
		sessions.add(as1);
		sessions.add(as2);
		
		activity.setSessions(sessions);
		
		boolean[] days = {false, false, true, false, false, false, false};
		activity.createSessions(DateLib.getDateObject(7, 4, 2015), 
				DateLib.getDateObject(4, 5, 2015), days);

		Assert.assertEquals(6, activity.getSessions().size());
	}

	@Test
	public void testString()
	{
		ActivityService as = new ActivityService();
		List<Activity> activities = as.findAllActivities();
		
		for (Activity activity : activities) {
			String activityString = "Activity: " + activity.getId() + ", '" + activity.getName() + "', '" 
		+ activity.getDescription() + "', '" + activity.getVenue() + "', "+activity.getMemberships().size() 
		+ " membership(s)" + ", "+activity.getSessions().size() + " session(s)";
			Assert.assertEquals(activityString, activity.toString());
		}
	}
}
