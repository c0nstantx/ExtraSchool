package controllers.service;

import models.domain.EntityBaseTest;

public class SessionRegisterServiceTest extends EntityBaseTest
{
	private SessionRegisterService srs;

	@Override
	public void beforeTest()
	{
		super.beforeTest();
		srs = new SessionRegisterService();
	}
}
