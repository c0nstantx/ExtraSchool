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

/**
 * SessionStatistics Class
 * Wrapper for registration statistics
 * 
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */

public class SessionStatistics {
	
	private Activity activity;
	private int numberOfCompletedSessions = 0;
	private int numberOfCancelledSessions = 0;	
	private Map<RegistrationStatus, Integer> registrationStatusNumbers = new HashMap<RegistrationStatus, Integer>();
	private SessionRegisterService service = new SessionRegisterService();
	
	public SessionStatistics(Activity activity) {
		this.activity = activity;
		initializeMap();
		calculateStatistics();
	}
	
	public int getNumberOfCompletedSessions() {
		return numberOfCompletedSessions;
	}
	
	public int getNumberOfCancelledSessions() {
		return numberOfCancelledSessions;
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
	}
	
	private void updateStatistics(ActivitySession session) {
		Iterator<Membership> it = activity.getMemberships().iterator();
		while (it.hasNext()) {
			User pupil = ((Membership)it.next()).getUser();
			SessionRegister register = service.findRegister(session, pupil);
			increaseCount(register.getStatus());
		}
	}
	
	private void increaseCount(RegistrationStatus status) {
		int count = registrationStatusNumbers.get(status) + 1;
		registrationStatusNumbers.put(status, count);
	}
}
