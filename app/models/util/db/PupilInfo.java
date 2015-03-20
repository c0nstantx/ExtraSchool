package models.util.db;

import java.util.Date;

import models.domain.User;
import models.domain.UserType;
import models.util.DateLib;

/**
 * PupilInfo class
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class PupilInfo {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Date birthDate;
	
	public PupilInfo(String username, String password, String firstName, String lastName, Date birthDate) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}
	
	public String getUserame() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public User createPupil() {
		return new User(username, password, UserType.Student, firstName, lastName, birthDate);
	}
	
	@Override
	public String toString() {
		return username + ", " + password + ", " + firstName + ", " + lastName + ", " + DateLib.dateAsString(birthDate);
	}
}
