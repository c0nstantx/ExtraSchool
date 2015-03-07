package controllers.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import models.domain.ActivitySession;
import models.domain.EntityBaseTest;
import models.util.DateLib;

import org.junit.Test;

import controllers.service.ActivitySessionService;

/**
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class ActivitySessionServiceTest extends EntityBaseTest {
	
	private ActivitySessionService ass;
	
	@Override
	public void beforeTest() {
		super.beforeTest();
		ass = new ActivitySessionService();
	}
	
	@Test
	public void findSessionsByDate()
	{
		List<ActivitySession> sessions = ass.findSessionsByDate(DateLib.getDateObject(10, 3, 2015));
		assertEquals(2, sessions.size());
	}
}
