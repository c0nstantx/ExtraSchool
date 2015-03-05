package models.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import models.persistence.DateLib;
import models.persistence.RegistrationStatus;
import models.persistence.SessionStatus;

import org.hibernate.annotations.Type;

/**
 * ActivitySession Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
@Entity
@Table(name = "activity_sessions")
public class ActivitySession {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "date")
	@Type(type = "date")
	private Date date;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private SessionStatus status;

	@ManyToOne()
	@JoinColumn(name = "activity_id")
	private Activity activity;
	
	@OneToMany(orphanRemoval=true, 
            cascade = CascadeType.ALL, 
            mappedBy = "session", fetch=FetchType.LAZY)
	private Set<SessionRegister> registers = new HashSet<SessionRegister>();

	/**
	 * Default constructor
	 */
	public ActivitySession() {}

	/**
	 * Constructor
	 * @param date the date this sessions takes place on
	 */
	public ActivitySession(Date date) {
		this.date = date;
		this.status = SessionStatus.Scheduled;
	}

	/**
	 * Constructor
	 * @param activity the activity this session is part of
	 * @param date the date this sessions takes place on
	 */
	public ActivitySession(Activity activity, Date date) {
		this.activity = activity;
		this.date = date;
		this.status = SessionStatus.Scheduled;
	}
	
	/**
	 * returns the id of the session
	 * @return session id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * sets the id of the session
	 * @param id session id
	 */
	@SuppressWarnings("unused")
	private void setId(Integer id) {
		this.id = id;
	}

	/**
	 * returns the date this session takes place on
	 * @return session date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * sets the date for this sessions
	 * @param date date this session is taking place on
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * returns the status code for this session
	 * @return session status code
	 */
	public SessionStatus getStatus() {
		return status;
	}
	
	/**
	 * sets the status code for this session
	 * @param status status code
	 */
	public void setStatus(SessionStatus status) {
		this.status = status;
	}

	/**
	 * returns the activity this session is part of
	 * @return parent activity 
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * sets the the activity this session is part of
	 * @param activity parent activity
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	/**
	 * returns the set of registers linked to this session
	 * @return set of registers
	 */
	public Set<SessionRegister> getRegisters() {
		return registers;
	}
	
	/**
	 * sets the set of registers linked to the activity
	 * @param registers set of registers
	 */
	public void setRegisters(Set<SessionRegister> registers) {
		this.registers = registers;
	}
	
	/**
	 * adds a new register to the set of registers
	 * @param user user the new register concerns
	 * @param status registration status code for this register record
	 * @param notes notes for this register record
	 */
	public void addRegister(User user, RegistrationStatus status, String notes) {
		SessionRegister register = new SessionRegister(user, this, status, notes);
		registers.add(register);
	}
	
	/**
	 * Returns a string representation of the session object
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		StringBuilder strbuilder = new StringBuilder();
		strbuilder.append("Session: ");
		strbuilder.append("'" + activity.getName() + "'" + ", ");
		strbuilder.append(DateLib.dateAsString(date) + ", ");
		strbuilder.append("'" + activity.getVenue() + "'" + ", ");
		strbuilder.append("'" + status + "'" + ", ");
		strbuilder.append(registers.size() + " register(s)");
		return strbuilder.toString();
	}
}
