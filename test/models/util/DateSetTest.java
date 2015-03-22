package models.util;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateSetTest
{

	@Test
	public void testConstructor()
	{
		try {
			Date dateStart = DateLib.createFromString("yyyy-MM-dd", "2015-03-17");
			Date dateEnd = DateLib.createFromString("yyyy-MM-dd", "2015-03-18");
			DateSet dateSet = new DateSet(dateStart, dateEnd);
			Assert.assertEquals(dateStart, dateSet.getStartDate());
			Assert.assertEquals(dateEnd, dateSet.getEndDate());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
