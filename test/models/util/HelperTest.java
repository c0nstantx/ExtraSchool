package models.util;

import java.util.Date;
import java.util.List;

import models.domain.Activity;
import models.domain.EntityBaseTest;

import org.junit.Assert;
import org.junit.Test;

import controllers.service.ActivityService;

/**
 * 
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 *
 */
public class HelperTest extends EntityBaseTest
{
	@Test
	public void testDates()
	{
		ActivityService as = new ActivityService();
		Activity activity = as.findActivityByName("Gymnastics");
		List<Date> dates = Helper.getActivityDates(activity);

		Assert.assertEquals(2, dates.size());
	}

	@Test
	public void testConstructor()
	{
		Helper helper = new Helper();
		Assert.assertEquals(Helper.class, helper.getClass());
	}
}
