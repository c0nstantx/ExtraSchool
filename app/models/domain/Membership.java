package models.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.util.DateLib;

/**
 * ActivityMembership Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
@Entity
@Table(name = "memberships")
public class Membership
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne()
	@JoinColumn(name = "activity_id")
	private Activity activity;
	
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "registration_date")
	private Date registrationDate;
	
	@Embedded
	private Report report = new Report();
	
	/**
	 * Default constructor
	 */
	public Membership() {}

	/**
	 * Constructor
	 * @param activity the activity that a user signed up for
	 * @param the user that signed up for this activity
	 * @param registrationDate the date the user signed up for the activity
	 */
	public Membership(Activity activity, User user, Date registrationDate) {
		this.activity = activity;
		this.user = user;
		this.registrationDate = registrationDate;
	}
	
	/**
	 * returns the id of the membership
	 * @return session id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * sets the id of the membership
	 * @param id membership id
	 */
	@SuppressWarnings("unused")
	private void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * returns the activity involved in this membership
	 * @return activity in membership
	 */
	public Activity getActivity() {
		return activity;
	}
	
	/**
	 * sets the activity involved in this membership
	 * @param activity the activity involved in this membership
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	/**
	 * returns the user involved in this membership
	 * @return user in membership
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * sets the user involved in this membership
	 * @param user the activity involved in this membership
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * returns the registration date the user signed up for this activity
	 * @return user registration date
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}
	
	/**
	 * sets the registration date the user signed up for this activity
	 * @param registrationDate user registration date
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	/**
	 * returns the report for the user-activity pair defined in the membership
	 * @return user report
	 */
	public Report getReport() {
		return report;
	}
	
	/**
	 * sets the report for the user-activity pair defined in the membership
	 * @param report user report
	 */
	public void setReport(Report report) {
		this.report = report;
	}
	
	/**
	 * Returns a string representation of the membership object
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		StringBuilder strbuilder = new StringBuilder();
		strbuilder.append("Membership: ");
		strbuilder.append(activity.getId() + ", ");
		strbuilder.append(user.getId() + ", ");
		strbuilder.append(DateLib.dateAsString(registrationDate));
		return strbuilder.toString();
	}
}
