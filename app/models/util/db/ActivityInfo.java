package models.util.db;

import java.util.Date;
import java.util.Iterator;

import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.Membership;
import models.domain.SessionStatus;
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
	private int[] pupilIds;
	
	public ActivityInfo(String name, String description, DateSet[] dateSets, String venue,
						String tutorUsername, String tutorPassword, String tutorFirstName,
						String tutorLastName, Date tutorBirthDate, int[] pupilIds) {
		this.name = name;
		this.description = description;
		this.dateSets = dateSets;
		this.venue = venue;
		this.tutorUsername = tutorUsername;
		this.tutorPassword = tutorPassword;
		this.tutorFirstName = tutorFirstName;
		this.tutorLastName = tutorLastName;
		this.tutorBirthDate = tutorBirthDate;
		this.pupilIds = pupilIds;
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
	
	public Membership[] setupActivity(User[] pupils, Date creationDate) {
		Activity activity = new Activity(name, description, venue);
		createAllSessions(activity);
		Membership[] memberships = new Membership[pupilIds.length + 1];
		memberships[0] = createAndAddTutor(activity, creationDate);
		for (int i = 1; i < memberships.length; i++) {
			memberships[i] = addPupil(activity, pupils[i - 1], creationDate);
		}
		return memberships;
	}
	
	private void createAllSessions(Activity activity) {
		for (int i = 0; i < dateSets.length; i++) {
			activity.createSessions(DateLib.copyDateObject(dateSets[i].getStartDate()), DateLib.copyDateObject(dateSets[i].getEndDate()), dateSets[i].getWeekdays());
		}
		setCompletedSessions(activity);
	}
	
	private Membership createAndAddTutor(Activity activity, Date creationDate) {
		User tutor = new User(tutorUsername, tutorPassword, UserType.Tutor, tutorFirstName, tutorLastName, tutorBirthDate);
		Membership tutorMembership = new Membership(activity, tutor, creationDate);
		activity.addMembership(tutorMembership);
		tutor.addMembership(tutorMembership);
		return tutorMembership;
	}
	
	private Membership addPupil(Activity activity, User pupil, Date creationDate) {
		Membership pupilMembership = new Membership(activity, pupil, creationDate);
		activity.addMembership(pupilMembership);
		pupil.addMembership(pupilMembership);
		return pupilMembership;
	}
	
	private void setCompletedSessions(Activity activity) {
		Iterator<ActivitySession> it = activity.getSessions().iterator();
		while (it.hasNext()) {
			ActivitySession session = (ActivitySession)it.next();
			if (DateLib.preceeds(session.getDate(), DBCreator.CurrentDate)) {
				session.setStatus(SessionStatus.Completed);
			}
		}
	}
}
