package models.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import models.persistence.RegistrationStatus;
import models.persistence.UserType;

/**
 * User Entity
 * Application's user data
 * 
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "user_type")
	@Enumerated(EnumType.STRING)
	private UserType userType = UserType.Student;

	@Embedded
	private Person person = new Person();
	
	@OneToMany(orphanRemoval=true, 
            cascade = CascadeType.ALL, 
            mappedBy = "user", fetch=FetchType.LAZY)
	private Set<Membership> memberships = new HashSet<Membership>();
	
	@OneToMany(orphanRemoval=true, 
            cascade = CascadeType.ALL, 
            mappedBy = "student", fetch=FetchType.LAZY)
	private Set<SessionRegister> registers = new HashSet<SessionRegister>();


    /**
	 * Default constructor
	 */
	public User() {}
	
	/**
	 * Constructor
	 * @param username the username for this user
	 * @param password the password for this user
	 * @param userType the user type (Admin, Tutor, Student)
	 * @param fname this user's first name
	 * @param fname this user's last name
	 * @param fname this user's birth date
	 */
	public User(String username, String password, UserType userType, String fName, String lName, Date birthDate) {
		this.username = username;
		this.password = password;
		this.userType = userType;
		person.setFirstName(fName);
		person.setLastName(lName);
		person.setBirthDate(birthDate);
	}
	
	/**
	 * returns the id of the user
	 * @return user id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * sets the id of the user
	 * @param id user id
	 */
	@SuppressWarnings("unused")
	private void setId(Integer id) {
		this.id = id;
	}

	/**
	 * returns the username of the user
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * sets the username of the user
	 * @param uname username
	 */
	public void setUsername(String uname) {
		username = uname;
	}

	/**
	 * returns the password of the user
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * sets the password of the user
	 * @param pwd password
	 */
	public void setPassword(String pwd) {
		password = pwd;
	}
	
	/**
	 * returns the user type of the user
	 * @return user type
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * sets the user type of the user
	 * @param userType user type
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	/**
	 * returns the embedded Person object for this user
	 * @return Person object
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * sets the embedded Person object for this user
	 * @param person Person object
	 */
	public void setPerson(Person person) {
		this.person = person;
	}
	
	/**
	 * returns the set of memberships for this user
	 * @return set of Membership objects
	 */
	public Set<Membership> getMemberships() {
		return memberships;
	}

	/**
	 * sets the set of memberships for this user
	 * @param memberships set of Membership objects
	 */
	public void setMemberships(Set<Membership> memberships) {
		this.memberships = memberships;
	}
	
	/**
	 * adds a new membership to the set of memberships
	 * @param activity new user activity
	 */
	public void addMembership(Membership membership) {
		memberships.add(membership);
	}

	/**
	 * returns the set of registers for this user
	 * @return set of SessionRegister objects
	 */
	public Set<SessionRegister> getRegisters() {
		return registers;
	}
	
	/**
	 * sets the set of registers for this user
	 * @param registers set of SessionRegister objects
	 */
	public void setRegisters(Set<SessionRegister> registers) {
		this.registers = registers;
	}
	
	/**
	 * adds a new register to the set of registers
	 * @param session the activity session this register refers to
	 * @param status the registration status for this register
	 * @param notes the notes about the user for this register
	 */
	public void addRegister(ActivitySession session, RegistrationStatus status, 
			String notes) {
		SessionRegister register = new SessionRegister(this, session, status, notes);
		registers.add(register);
	}

	@Override
	public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof User 
        		&& this.getUsername() == ((User) other).getUsername()) {
    		return true;
        }
        return false;
	}
	
	/**
	 * Returns a string representation of the Activity object
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		StringBuilder strbuilder = new StringBuilder();
		strbuilder.append("User: ");
		strbuilder.append((getId() == null ? -1 : getId()) + ", ");
		strbuilder.append("'" + getUsername() + "'" + ", ");
		strbuilder.append("'" + getPassword() + "'" + ", ");
		strbuilder.append("'" + getUserType() + "'" + ", ");
		strbuilder.append(getPerson().toString());
		return strbuilder.toString();
	}
}
