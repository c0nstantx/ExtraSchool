package models.domain;

import static org.junit.Assert.assertEquals;
import models.persistence.SessionStatus;
import models.util.DateLib;

import org.junit.Test;

/**
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class ActivitySessionTest extends EntityBaseTest {
	@Test
	public void testActivitySession() {
		Activity a = new Activity("Basketball Club", "Can you play the game?", "O.A.K.A. Basketball Court");
		ActivitySession as1 = new ActivitySession(a, DateLib.getDateObject(1, 4, 2015));
		new ActivitySession(a, DateLib.getDateObject(3, 4, 2015));
		new ActivitySession(a, DateLib.getDateObject(8, 4, 2015));
		new ActivitySession(a, DateLib.getDateObject(10, 4, 2015));
		assertEquals(4, a.getSessions().size());
		assertEquals(a, as1.getActivity());
		assertEquals(SessionStatus.Scheduled, as1.getStatus());
		assertEquals(4, DateLib.getWeekday(as1.getDate()));
	}
}
