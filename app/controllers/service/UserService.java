package controllers.service;

import java.util.Date;
import java.util.List;

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
	 * Creates new User object and attaches it to the database
	 * Prerequisites:
	 * - the username is not already in use
	 * @param username the username for this user
	 * @param password the password for this user
	 * @param userType user type
	 * @param firstName user's first name
	 * @param lastName user's last name
	 * @param birthDate user's birth date
	 * @return User|null The created User object or null
	 */
	public User createUser(String username, String password, UserType userType, 
			String firstName, String lastName, Date birthDate) {
		if (findUserByUsername(username) != null) {
			return null;
		}
		User user = new User(username, password, userType, firstName, lastName, birthDate);
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		return user;
	}

	/**
	 * Updates user
	 * @param user
	 * @return true if update successful, false otherwise
	 */
	public boolean updateUser(User user) {
		User searchUser = findUserByUsername(user.getUsername());
		if (searchUser != null && user.getId() == searchUser.getId()) {
			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
			return true;
		}
		return false;
	}
	
	/**
	 * Deletes user
	 * Prerequisites:
	 * - the user must not have any memberships in activities
	 * @param user
	 * @return true if deletion successful, false otherwise
	 */
	public boolean deleteUser(User user) {
		User searchUser = findUserByUsername(user.getUsername());
		if (searchUser != null && searchUser.getMemberships().size() == 0) {
			System.out.println("Memberships: " + searchUser.getMemberships().size());
			em.getTransaction().begin();
			em.createQuery("DELETE FROM User u WHERE u.id = :id")
			.setParameter("id", user.getId())
			.executeUpdate();
			em.getTransaction().commit();
			return true;
		}
		System.out.println("Memberships: " + searchUser.getMemberships().size());
		return false;
	}

	/**
	 * Finds all users
	 */
	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		List<User> users = em.createQuery("SELECT u FROM User u").getResultList();
		return users;
	}

	/**
	 * Searches for a user with a specific username
	 * @param username
	 * @return User|null If the user exists return the User object, else return null
	 */
	public User findUserByUsername(String username) {
		try {
			User user = (User) em.createQuery("SELECT u FROM User u WHERE u.username = :username")
					.setParameter("username", username)
					.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}
}
