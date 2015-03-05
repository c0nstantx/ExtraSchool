package controllers.service;

import javax.persistence.EntityManager;

import models.persistence.JPAUtil;

/**
 * Base service class for Entity services
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 *
 */
public class BaseService
{
	protected EntityManager em;
	
	/**
	 * Constructor
	 */
	public BaseService()
	{
		em = JPAUtil.getCurrentEntityManager();
	}
}
