package controllers.service;

import models.domain.EntityBaseTest;

public class MembershipServiceTest extends EntityBaseTest
{
	private MembershipService ms;

	@Override
	public void beforeTest()
	{
		super.beforeTest();
		ms = new MembershipService();
	}
}
