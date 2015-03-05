package controllers.service;

import java.util.Date;

import javax.persistence.NoResultException;

import models.domain.User;
import models.persistence.UserType;

/**
 * Service for User Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class UserService extends BaseService {
	
	/**
	 * Creates new user and attaches it to the database
	 * Prerequisites:
	 * - no other user account exists for the same person
	 * - the username is not already in use
	 * @param username the username for this user
	 * @param password the password for this user
	 * @param userType user type
	 * @param identityNo user's identity number
	 * @param firstName user's first name
	 * @param lastName user's last name
	 * @param birthDate user's birth date
	 * @return the created User object
	 */
	public User createUser(String username, String password, UserType userType, String identityNo, String firstName, String lastName, Date birthDate) {
		if (accountAlreadyExists(identityNo)) {
			System.out.println("createUser(): Account Already Exists");
			return null;
		}
		if (usernameAlreadyExists(username)) {
			System.out.println("createUser(): Username Already Exists");
			return null;
		}
		User user = new User(username, password, userType, identityNo, firstName, lastName, birthDate);
		em.persist(user);
		return null;
	}
	
	/**
	 * Update existing user
	 * @return
	 */
	public void updateUser() {
		
	}
	
	/**
	 * Delete existing user
	 * @return
	 */
	public void deleteUser() {
		
	}
	
	/**
	 * Checks if a specific user already has an account
	 * @param identityNo person's identity number
	 * @return true if an account already exists, false otherwise
	 */
	private boolean accountAlreadyExists(String identityNo) {
		User user = null;
		System.out.println("Trying: " + identityNo);
		try {
			user = (User)em.createQuery("SELECT u FROM User u WHERE u.person.identityNo = :identityNo")
				.setParameter("identityNo", identityNo)
				.getSingleResult();
		}
		catch (NoResultException exc) {
			System.out.println("Exception!");
		}
		return (user != null ? true : false);
	}
	
	/**
	 * Checks if a specific username is already in use
	 * @param username the username to check
	 * @return true if the username is in use, false otherwise
	 */
	private boolean usernameAlreadyExists(String username) {
		User user = null;
		System.out.println("Trying: " + username);
		try {
			user = (User)em.createQuery("SELECT u FROM User u WHERE u.username = :username")
				.setParameter("username", username)
				.getSingleResult();
		}
		catch (NoResultException exc) {
			System.out.println("Exception!");
		}
		return (user != null ? true : false);
	}
}
