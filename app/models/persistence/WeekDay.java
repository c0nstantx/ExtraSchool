package models.persistence;

/**
 * Custom enumerated type for week days
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
public enum WeekDay {
	Sunday (1),
	Monday (2),
	Tuesday (3),
	Wednesday (4),
	Thursday (5),
	Friday (6),
	Saturday (7);
	
	WeekDay(int day) {
		this.day = day;
	}
	
	private final int day;
}
