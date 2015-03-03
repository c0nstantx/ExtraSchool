package models.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;

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
	private UserType userType = UserType.STUDENT;

	@OneToMany()
	private List<Activity> activities;

	@Embedded
	private Person person = new Person();

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
}
