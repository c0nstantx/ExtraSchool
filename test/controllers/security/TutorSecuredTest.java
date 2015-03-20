package controllers.security;

import play.mvc.Http.Flash;
import play.mvc.Http.Session;
import models.domain.EntityBaseTest;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;
import play.mvc.Http.Context;

public class TutorSecuredTest extends EntityBaseTest
{
	@Test
	public void testUsername()
	{
		TutorSecured secured = new TutorSecured();
		Context ctxMocked = mock(Context.class);
		Session sessionMocked = mock(Session.class);
		when(ctxMocked.session()).thenReturn(sessionMocked);
		when(sessionMocked.get("username")).thenReturn("kchristofilos");
		
		Assert.assertEquals("kchristofilos", secured.getUsername(ctxMocked));
	}
	
	@Test
	public void testAdminUsername()
	{
		TutorSecured secured = new TutorSecured();
		Context ctxMocked = mock(Context.class);
		Session sessionMocked = mock(Session.class);
		when(ctxMocked.session()).thenReturn(sessionMocked);
		when(sessionMocked.get("username")).thenReturn("admin");
		
		Assert.assertEquals("admin", secured.getUsername(ctxMocked));
	}
	
	@Test
	public void testNullUsername()
	{
		TutorSecured secured = new TutorSecured();
		Context ctxMocked = mock(Context.class);
		Session sessionMocked = mock(Session.class);
		Flash flashMocked = mock(Flash.class);
		when(ctxMocked.session()).thenReturn(sessionMocked);
		when(ctxMocked.flash()).thenReturn(flashMocked);
		when(flashMocked.put("error", "Only Tutors can access that page.")).thenReturn("");
		when(sessionMocked.get("username")).thenReturn(null);
		
		Assert.assertNull(secured.getUsername(ctxMocked));
	}
	
	@Test
	public void testUsernameNonAdmin()
	{
		TutorSecured secured = new TutorSecured();
		Context ctxMocked = mock(Context.class);
		Session sessionMocked = mock(Session.class);
		Flash flashMocked = mock(Flash.class);
		when(ctxMocked.session()).thenReturn(sessionMocked);
		when(ctxMocked.flash()).thenReturn(flashMocked);
		when(flashMocked.put("error", "Only Tutors can access that page.")).thenReturn("");
		when(sessionMocked.get("username")).thenReturn("nonAdmin");
		
		Assert.assertNull(secured.getUsername(ctxMocked));
	}
}
