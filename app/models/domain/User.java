package models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import models.persistence.UserType;

/**
 * User Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
@Entity
@Table(name="users")
public class User {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="username")
	private String Username;

	@Column(name="password")
	private String Password;

	@Column(name="user_type")
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	public Integer getId() {
		return id;
	}

	private void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
}
