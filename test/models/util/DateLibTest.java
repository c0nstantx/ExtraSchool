package models.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import models.domain.EntityBaseTest;

import org.junit.Test;

/**
 * 
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 *
 */
public class DateLibTest extends EntityBaseTest
{
	@Test
	public void datesTest()
	{
		Date date1 = DateLib.getDateObject(); // get system time
		Date date2 = DateLib.getDateObject(); // get system time
		assertEquals(0, DateLib.compare(date1, date2));
		
		Date date3 = DateLib.getDateObject(25, 3, 2015); // date3 is 25.03.2015 12:00
		Date date4 = DateLib.getDateObject(25, 3, 2015, 13, 30, 0); // date4 is 25.03.2015 13.30
		assertEquals(-1, DateLib.compare(date3, date4));
		
		DateLib.addHours(date4, -1); // date4 is 25.03.2015 12.30
		DateLib.addMinutes(date4, -30); // date4 is 25.03.2015 12.00
		assertEquals(true, DateLib.equals(date3, date4));
		
		date1 = DateLib.getDateObject(26, 03, 2015);
		assertFalse(DateLib.succeeds(date3, date1));
		assertTrue(DateLib.preceeds(date3, date1));
		assertFalse(DateLib.succeeds(date3, date4));
		
		date2 = DateLib.copyDateObject(date1); // date2 is 26.03.2015 12.00
		DateLib.addMonths(date1, 1); // date 1 is 26.04.2015 12.00
		String dateString2_1 = DateLib.dateAsString(date2).substring(4);
		String dateString2_2 = "26/03/2015 12:00";
		assertEquals(dateString2_1, dateString2_2);
		
		assertEquals(4, DateLib.getWeekday(date3)); // 25.03.2015 is Wed (4)
		assertEquals(5, DateLib.getWeekday(date2)); // 26.03.2015 is Thur (4)
	}
}
