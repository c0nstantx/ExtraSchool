package controllers.service;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import models.domain.ActivitySession;


/**
 * Service for Membership Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 *
 */
public class ActivitySessionService extends BaseService {
	
	/**
	 * Find all activity sessions on a given date
	 * @param date date of interest
	 * @return list of sessions
	 */
	public List<ActivitySession> findSessionsByDate(Date date) {
		Query query = em.createQuery("SELECT a FROM ActivitySession a WHERE a.date = :date")
				.setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<ActivitySession> sessions = query.getResultList();
		return sessions;
	}
	
	/**
	 * Find all activity sessions on any date
	 * @return list of sessions
	 */
	public List<ActivitySession> findAllSessions() {
		Query query = em.createQuery("SELECT a FROM ActivitySession a");
		@SuppressWarnings("unchecked")
		List<ActivitySession> sessions = query.getResultList();
		return sessions;
	}
}
