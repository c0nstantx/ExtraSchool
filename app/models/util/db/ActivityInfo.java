package models.util.db;

import java.util.Date;

import models.domain.Activity;
import models.domain.Membership;
import models.domain.User;
import models.domain.UserType;
import models.util.DateLib;
import models.util.DateSet;

/**
 * ActivityInfo class
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class ActivityInfo {
	private String name;
	private String description;
	private DateSet[] dateSets;
	private String venue;
	private String tutorUsername;
	private String tutorPassword;
	private String tutorFirstName;
	private String tutorLastName;
	private Date tutorBirthDate;
	
	public ActivityInfo(String name, String description, DateSet[] dateSets, String venue,
						String tutorUsername, String tutorPassword, String tutorFirstName,
						String tutorLastName, Date tutorBirthDate) {
		this.name = name;
		this.description = description;
		this.dateSets = dateSets;
		this.venue = venue;
		this.tutorUsername = tutorUsername;
		this.tutorPassword = tutorPassword;
		this.tutorFirstName = tutorFirstName;
		this.tutorLastName = tutorLastName;
		this.tutorBirthDate = tutorBirthDate;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getVenue() {
		return venue;
	}
	
	public String getTutorUsername() {
		return tutorUsername;
	}
	
	public int getTotalNumberOfSessions() {
		int sessions = 0;
		for (int i = 0; i < dateSets.length; i++) {
			sessions += getNumberOfSessions(dateSets[i]);
		}
		return sessions;
	}
	
	private int getNumberOfSessions(DateSet dateSet) {
		int sessions = 0;
		Date startDate = DateLib.copyDateObject(dateSet.getStartDate());
		Date endDate = DateLib.copyDateObject(dateSet.getEndDate());
		while (!DateLib.succeeds(startDate, endDate)) {
			if (dateSet.getWeekdays()[DateLib.getWeekday(startDate) - 1]) {
				sessions++;
			}
			DateLib.addDays(startDate, 1);
		}
		return sessions;
	}
	
	public Membership createActivityWithTutor(Date creationDate) {
		Activity activity = new Activity(name, description, venue);
		createAllSessions(activity);
		User tutor = new User(tutorUsername, tutorPassword, UserType.Tutor, tutorFirstName, tutorLastName, tutorBirthDate);
		Membership membership = new Membership(activity, tutor, creationDate);
		activity.addMembership(membership);
		tutor.addMembership(membership);
		return membership;
	}
	
	private void createAllSessions(Activity activity) {
		for (int i = 0; i < dateSets.length; i++) {
			activity.createSessions(DateLib.copyDateObject(dateSets[i].getStartDate()), DateLib.copyDateObject(dateSets[i].getEndDate()), dateSets[i].getWeekdays());
		}
	}
}
