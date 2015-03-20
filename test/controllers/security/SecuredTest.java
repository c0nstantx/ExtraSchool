package controllers.security;

import play.mvc.Http.Session;
import play.mvc.Results.Redirect;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;
import play.mvc.Http.Context;

public class SecuredTest
{
	@Test
	public void testUsername()
	{
		Secured secured = new Secured();
		Context ctxMocked = mock(Context.class);
		Session sessionMocked = mock(Session.class);
		when(ctxMocked.session()).thenReturn(sessionMocked);
		when(sessionMocked.get("username")).thenReturn("user");
		
		Assert.assertEquals("user", secured.getUsername(ctxMocked));
	}

	@Test
	public void testUnauthorized()
	{
		Secured secured = new Secured();
		Context ctxMocked = mock(Context.class);
		Session sessionMocked = mock(Session.class);
		when(ctxMocked.session()).thenReturn(sessionMocked);
		when(sessionMocked.get("username")).thenReturn("user");
		
		Assert.assertEquals(secured.onUnauthorized(ctxMocked).getClass(), Redirect.class);
	}

	@Test
	public void testUnauthorizedUser()
	{
		Secured secured = new Secured();
		Context ctxMocked = mock(Context.class);
		Session sessionMocked = mock(Session.class);
		when(ctxMocked.session()).thenReturn(sessionMocked);
		when(sessionMocked.get("username")).thenReturn(null);
		
		Assert.assertEquals(secured.onUnauthorized(ctxMocked).getClass(), Redirect.class);
	}
}
