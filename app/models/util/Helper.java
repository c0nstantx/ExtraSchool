package models.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import models.domain.Activity;
import models.domain.ActivitySession;

public class Helper {

//	public static List<ActivitySession> getOrderedSessions(Set<ActivitySession> sessions) {
//		List<ActivitySession> sessionsList =  new ArrayList<ActivitySession>(sessions);
//		sessionsList.sort(new Comparator<ActivitySession>() {
//
//			@Override
//			public int compare(ActivitySession o1, ActivitySession o2) {
//				if (o1.getDate().before(o2.getDate())) {
//					return 1;
//				}
//				return 0;
//			}
//		});
//		return sessionsList;
//	}
	
	public static List<Date> getActivityDates(Activity activity) {
		List<Date> dates = new ArrayList<Date>();
		for (ActivitySession session : activity.getSessions()) {
			dates.add(session.getDate());
		}
		dates.sort(new Comparator<Date>() {

			@Override
			public int compare(Date d1, Date d2) {
				if (d1.before(d2)) {
					return 1;
				}
				return 0;
			}
		});
		return dates;
	}
}