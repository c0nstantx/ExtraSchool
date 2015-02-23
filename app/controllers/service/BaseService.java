package controllers.service;

import javax.persistence.EntityManager;

import models.persistence.JPAUtil;

/**
 * Base service class for Entity services
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
public class BaseService
{
	protected EntityManager em;
	
	public BaseService(EntityManager em)
	{
//		em = JPAUtil.getCurrentEntityManager();
		this.em = em;
	}
}
