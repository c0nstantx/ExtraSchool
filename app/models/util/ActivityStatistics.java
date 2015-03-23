package models.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import controllers.service.SessionRegisterService;
import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.Membership;
import models.domain.RegistrationStatus;
import models.domain.SessionRegister;
import models.domain.SessionStatus;
import models.domain.User;
import models.domain.UserType;

/**
 * ActivityStatistics Class
 * Wrapper for registration statistics for all activity sessions
 * 
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */

public class ActivityStatistics {
	
	private Activity activity;
	private int numberOfCompletedSessions = 0;
	private int numberOfCancelledSessions = 0;	
	private int groupSize;
	private int totalNumberOfRegisters;
	private Map<RegistrationStatus, Integer> registrationStatusNumbers = new HashMap<RegistrationStatus, Integer>();
	private SessionRegisterService service = new SessionRegisterService();
	
	public ActivityStatistics(Activity activity) {
		this.activity = activity;
		groupSize = activity.getMemberships().size() - 1;
		initializeMap();
		calculateStatistics();
	}
	
	public int getNumberOfCompletedSessions() {
		return numberOfCompletedSessions;
	}
	
	public int getNumberOfCancelledSessions() {
		return numberOfCancelledSessions;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Session Statistics for activity '" + activity.getName() +"'\n");
		str.append("Group size: " + groupSize + "\n");
		str.append("Completed sessions: " + numberOfCompletedSessions + "\n");
		str.append("Cancelled sessions: " + numberOfCancelledSessions + "\n");
		str.append("Total number of registers: " + totalNumberOfRegisters + "\n");
		str.append("Present: " + registrationStatusNumbers.get(RegistrationStatus.Present) + "/" + totalNumberOfRegisters  + "\n");
		str.append("Absent due to illness: " + registrationStatusNumbers.get(RegistrationStatus.AbsentDueToIllness) + "/" + totalNumberOfRegisters  + "\n");
		str.append("Absent due to injury: " + registrationStatusNumbers.get(RegistrationStatus.AbsentDueToInjury) + "/" + totalNumberOfRegisters  + "\n");
		str.append("Absent without permission: " + registrationStatusNumbers.get(RegistrationStatus.AbsentWithoutPermission) + "/" + totalNumberOfRegisters  + "\n");
		str.append("Absent with permission: " + registrationStatusNumbers.get(RegistrationStatus.AbsentWithPermission) + "/" + totalNumberOfRegisters  + "\n");
		return str.toString();
	}
	
	private void initializeMap() {
		registrationStatusNumbers.put(RegistrationStatus.Present, 0);
		registrationStatusNumbers.put(RegistrationStatus.AbsentDueToIllness, 0);
		registrationStatusNumbers.put(RegistrationStatus.AbsentDueToInjury, 0);
		registrationStatusNumbers.put(RegistrationStatus.AbsentWithoutPermission, 0);
		registrationStatusNumbers.put(RegistrationStatus.AbsentWithPermission, 0);
	}
	
	private void calculateStatistics() {
		Iterator<ActivitySession> it = activity.getSessions().iterator();
		while (it.hasNext()) {
			ActivitySession session = (ActivitySession)it.next();
			if (session.getStatus() == SessionStatus.Completed) {
				numberOfCompletedSessions++;
				updateStatistics(session);
			}
			else if (session.getStatus() == SessionStatus.Cancelled) {
				numberOfCancelledSessions++;
			}
		}
		totalNumberOfRegisters = numberOfCompletedSessions * groupSize;
	}
	
	private void updateStatistics(ActivitySession session) {
		Iterator<Membership> it = activity.getMemberships().iterator();
		while (it.hasNext()) {
			User user = ((Membership)it.next()).getUser();
			if (user.getUserType() == UserType.Student) {
				SessionRegister register = service.findRegister(session, user);
				if (register == null) System.out.println("NULL");
				increaseCount(register.getStatus());
			}
		}
	}
	
	private void increaseCount(RegistrationStatus status) {
		int count = registrationStatusNumbers.get(status) + 1;
		registrationStatusNumbers.put(status, count);
	}
}
