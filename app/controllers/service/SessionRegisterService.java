package controllers.service;

import java.util.List;

import javax.persistence.NoResultException;

import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.RegistrationStatus;
import models.domain.SessionRegister;
import models.domain.User;
import models.domain.UserType;

/**
 * Service for User Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class SessionRegisterService extends BaseService {
	
	/**
	 * Creates new SessionRegister object and attaches it to the database
	 * Prerequisites:
	 * - the user can only be a student
	 * - the user must already be registered for the activity to which the session belongs
	 * - there must not already be a SessionRegister object linked to the same session and user
	 * @param session the activity session to create a register for
	 * @param user the student for whom the register is created
	 * @param status the registration status for this student during this session
	 * @param notes the notes about this student regarding this session
	 * @return SessionRegister|null The created SessionRegister object or null
	 */
	public SessionRegister createRegister(ActivitySession session, User user, RegistrationStatus status, String notes) { // tested
		if (user.getUserType() != UserType.Student) {
			return null;
		}
		UserService us = new UserService();
		if (!us.isRegisteredFor(session.getActivity(), user)) {
			return null;
		}
		if (findRegister(session, user) != null) {
			return null;
		}
		SessionRegister register = new SessionRegister(user, session, status, notes);
		session.addRegister(register);
		user.addRegister(register);
		em.getTransaction().begin();
		em.persist(register);
		em.getTransaction().commit();
		return register;
	}
	
	/**
	 * Updates session register
	 * @param register the session register to update
	 * @return true if update successful, false otherwise
	 */
	public boolean updateRegister(SessionRegister register) { // tested
		if (findRegister(register.getSession(), register.getStudent()) != null) {
			em.getTransaction().begin();
			em.merge(register);
			em.getTransaction().commit();
			return true;
		}
		return false;
	}
	
	/**
	 * Deletes session register
	 * @param register the session register to delete
	 * @return true if deletion successful, false otherwise
	 */
	public boolean deleteRegister(SessionRegister register) { // tested
		if (findRegister(register.getSession(), register.getStudent()) != null) {
			em.getTransaction().begin();
			em.createQuery("DELETE FROM SessionRegister s WHERE s.id = :id")
			.setParameter("id", register.getId())
			.executeUpdate();
			em.getTransaction().commit();
			return true;
		}
		return false;
	}
	
	/**
	 * Finds all session registers
	 * @return list of registers
	 */
	public List<SessionRegister> findAllRegisters() {
		@SuppressWarnings("unchecked")
		List<SessionRegister> results = em.createQuery("SELECT s FROM SessionRegister s")
				.getResultList();
		return results;
	}
	
	/**
	 * Finds all session registers for a specific user
	 * @param user user
	 * @return list of registers
	 */
	public List<SessionRegister> findRegistersByUser(User user) { // tested
		@SuppressWarnings("unchecked")
		List<SessionRegister> results = em.createQuery("SELECT s FROM SessionRegister s WHERE s.student.id = :id")
			.setParameter("id", user.getId())
			.getResultList();
		return results;
	}
	
	/**
	 * Finds all session registers for a specific activity
	 * @param activity activity
	 * @return list of registers
	 */
	public List<SessionRegister> findRegistersBySession(ActivitySession session) { // tested
		@SuppressWarnings("unchecked")
		List<SessionRegister> results = em.createQuery("SELECT s FROM SessionRegister s WHERE s.session.id = :id")
			.setParameter("id", session.getId())
			.getResultList();
		return results;
	}

	/**
	 * Find a session register by its key (activity session, user)
	 * @param session activity session
	 * @param user user
	 * @return register with the specified key or null
	 */
	public SessionRegister findRegister(ActivitySession session, User user) { // tested
		try {
			SessionRegister register = (SessionRegister)em
					.createQuery("SELECT s FROM SessionRegister s WHERE s.session.id = :seId AND s.student.id = :usId")
					.setParameter("seId", session.getId())
					.setParameter("usId", user.getId())
					.getSingleResult();
			return register;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Finds all session registers for a specific activity-user pair
	 * @param activity activity
	 * @param user user
	 * @return list of registers
	 */
	public List<SessionRegister> findRegistersByActivityUser(Activity activity, User user) { // tested
		@SuppressWarnings("unchecked")
		List<SessionRegister> results = em.createQuery("SELECT s FROM SessionRegister s WHERE s.student.id = :usId AND s.session.activity.id = :acId")
			.setParameter("usId", user.getId())
			.setParameter("acId", activity.getId())
			.getResultList();
		return results;
	}
}
