package models.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import models.util.DateLib;

/**
 * Person Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
@Embeddable
public class Person {
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@Column(name = "birth_date")
	private Date birthDate;

	/**
	 * Default constructor
	 */
	public Person() {}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String toString() {
		StringBuilder strbuilder = new StringBuilder();
		strbuilder.append("'" + firstName + "'" + ", ");
		strbuilder.append("'" + lastName + "'" + ", ");
		strbuilder.append(DateLib.dateAsString(birthDate));
		return strbuilder.toString();
	}
}
