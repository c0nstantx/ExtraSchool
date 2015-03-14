package models.util;

import org.junit.Assert;
import org.junit.Test;

import models.domain.EntityBaseTest;
import models.domain.User;
import controllers.service.UserService;

/**
 * 
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 *
 */
public class AuthenticatorTest extends EntityBaseTest
{
	@Test
	public void authenticate()
	{
		UserService us = new UserService();
		User user = us.findUserByUsername("kchristofilos");
		
		Assert.assertNull(Authenticator.authenticate("kchristofilos", "123456789"));
		Assert.assertNull(Authenticator.authenticate("kostasx", "123456"));
		Assert.assertEquals(user, Authenticator.authenticate("kchristofilos", "123456"));
	}
}
