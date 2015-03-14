package models.util;

import controllers.service.UserService;
import models.domain.User;

/**
 * Authenticator
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class Authenticator {
	/**
	 * @param username
	 * @param password
	 * @return User|null
	 */
	public static User authenticate(String username, String password) {
		UserService us = new UserService();
		User user = us.findUserByUsername(username);
		if (null != user && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}
}
