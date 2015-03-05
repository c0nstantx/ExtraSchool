package controllers.service;

import models.domain.EntityBaseTest;
import models.persistence.DateLib;
import models.persistence.UserType;

import org.junit.Test;

public class UserServiceTest extends EntityBaseTest
{
	private UserService us;

	@Override
	public void beforeTest() {
		super.beforeTest();
		us = new UserService();
	}
	
	@Test
	public void addNewUsers() {
		//User u1 = new User("kchristofilos", "123456", UserType.Tutor, "Konstantinos", "Christofilos", "id111", CalendarUtilities.getDate(21, 12, 1985));
		us.createUser("kchristofilos", "123456", UserType.Tutor, "id111", "Konstantinos", "Christofilos", DateLib.getDateObject(21, 12, 1985));
		us.createUser("sok", "123456", UserType.Tutor, "id114", "Sokratis", "Alafouzos", DateLib.getDateObject(21, 12, 1985));
	}
}
