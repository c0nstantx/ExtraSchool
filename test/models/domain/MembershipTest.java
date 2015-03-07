package models.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import models.util.DateLib;
import controllers.service.ActivityService;
import controllers.service.UserService;

/**
 * 
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 *
 */
public class MembershipTest extends EntityBaseTest
{

	@Test
	public void membershipReport()
	{
		ActivityService as = new ActivityService();
		UserService us = new UserService();
		
		Activity activity = as.findByName("activity1");
		User user = us.findUserByUsername("sok");
		Membership membership = new Membership();
		membership.setActivity(activity);
		membership.setUser(user);
		membership.setRegistrationDate(DateLib.getDateObject(3, 3, 2015));
		
		Report report = new Report();
		report.setComment("Fair enough");
		report.setGrade(9.5);
		report.setPublicationDate(new Date());
		
		membership.setReport(report);
		
		em.persist(membership);
		
		Assert.assertEquals(9.5, membership.getReport().getGrade(), 0.001);
	}

	@Test
	public void membershipString()
	{
		ActivityService as = new ActivityService();
		List<Activity> activities = as.findAll();
		
		for (Activity activity : activities) {
			Set<Membership> memberships = activity.getMemberships();
			for (Membership membership : memberships) {
				String membershipString = "Membership: "+membership.getId().toString()+", "+activity.getId().toString() 
						+ ", "+membership.getUser().getId().toString() 
						+ ", "+DateLib.dateAsString(membership.getRegistrationDate())
						+ ", "+ membership.getReport().getPublished()
						+ ", "+DateLib.dateAsString(membership.getReport().getPublicationDate())
						+ ", "+membership.getReport().getGrade()
						+", '"+membership.getReport().getComment()+"'";
				Assert.assertEquals(membershipString, membership.toString());
			}
		}
	}
}
