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
 * 
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
@Entity
@Table(name = "users")
public class User
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "username", nullable = false)
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

	public User() {}

	public User(String uname, String pwd)
	{
		username = uname;
		password = pwd;
	}

	public User(String uname, String pwd, String fName, String lName)
	{
		username = uname;
		password = pwd;
		person.setFirstName(fName);
		person.setLastName(lName);
	}

	public User(String uname, String pwd, String fName, String lName, UserType uType)
	{
		username = uname;
		password = pwd;
		userType = uType;
		person.setFirstName(fName);
		person.setLastName(lName);
	}
	
	public Integer getId()
	{
		return id;
	}

	private void setId(Integer id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String uname)
	{
		username = uname;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String pwd)
	{
		password = pwd;
	}
	
	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}
	
	public Set<Membership> getMemberships() {
		return memberships;
	}
	
	public void setMemberships(Set<Membership> memberships) {
		this.memberships = memberships;
	}
	
	public void addMembership(Activity activity)
	{
		Membership membership = new Membership(activity, this, new Date());
		memberships.add(membership);
	}
	
	public Set<SessionRegister> getRegisters() {
		return registers;
	}
	
	public void setRegisters(Set<SessionRegister> registers) {
		this.registers = registers;
	}
	
	public void addRegister(ActivitySession session, RegistrationStatus status, String notes) {
		SessionRegister register = new SessionRegister(this, session, status, notes);
		registers.add(register);
	}
}
