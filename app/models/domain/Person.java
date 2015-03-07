package models.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

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
	@Type(type = "date")
	private Date birthDate;

	/**
	 * Default constructor
	 */
	public Person() {}
	
	/**
	 * returns the first name of the person
	 * @return person first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * sets the first name of the person
	 * @param firstName person first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * returns the last name of the person
	 * @return person last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * sets the last name of the person
	 * @param lastName person last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * returns the birth date of the person
	 * @return person birth date
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * sets the birth date of the person
	 * @param birthDate person birth date
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	/**
	 * returns the full name of the person
	 * @return person full name
	 */
	public String getFullName() {
		return firstName + " " + lastName;
	}

	/**
	 * Returns a string representation of the Person object
	 * @return a string representation of the object
	 */
	public String toString() {
		StringBuilder strbuilder = new StringBuilder();
		strbuilder.append("'" + firstName + "'" + ", ");
		strbuilder.append("'" + lastName + "'" + ", ");
		strbuilder.append(DateLib.dateAsString(birthDate));
		return strbuilder.toString();
	}
}
