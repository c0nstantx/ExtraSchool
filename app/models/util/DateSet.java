package models.util;

import java.util.Date;

/**
 * DateSet class
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class DateSet {
	private Date startDate;
	private Date endDate;
	private boolean[] weekdays;
	
	public DateSet(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		weekdays = new boolean[] {false, false, false, false, false, false, false};
	}
	
	public DateSet(Date startDate, Date endDate, boolean[] weekdays) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.weekdays = weekdays;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public boolean[] getWeekdays() {
		return weekdays;
	}
	
	@Override
	public String toString() {
		return DateLib.dateAsString(startDate) + " --> " + DateLib.dateAsString(endDate);
	}
}
