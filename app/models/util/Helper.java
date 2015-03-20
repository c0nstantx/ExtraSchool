package models.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import models.domain.Activity;
import models.domain.ActivitySession;

public class Helper {
	
	public static List<Date> getActivityDates(Activity activity) {
		List<Date> dates = new ArrayList<Date>();
		for (ActivitySession session : activity.getSessions()) {
			dates.add(session.getDate());
		}
		Collections.sort(dates);
		return dates;
	}
}