package models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SessionRegister Entity
 * Describes student's status in an activity's session (absences)
 * 
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
@Entity
@Table(name = "session_registers")
public class SessionRegister {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private User student;
	
	@ManyToOne()
	@JoinColumn(name = "activity_session_id")
	private ActivitySession session;
	
	@Column(name = "registration_status")
	@Enumerated(EnumType.STRING)
	private RegistrationStatus status;
	
	@Column(name = "notes")
	private String notes;

	/**
	 * Default constructor
	 */
	public SessionRegister() {}
	
	/**
	 * Constructor
	 * @param student the user who is registered
	 * @param session the session for which the user is registered
	 * @param status the user status entered for this session
	 * @param notes the notes entered about the user for this session
	 */
	public SessionRegister(User student, ActivitySession session, RegistrationStatus status, String notes) {
		this.student = student;
		this.session = session;
		this.status = status;
		this.notes = notes;
	}
	
	/**
	 * returns the id of the register
	 * @return register id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * sets the id of the register
	 * @param id register id
	 */
	@SuppressWarnings("unused")
	private void setId(Integer id) {
		this.id = id;
	}

	/**
	 * returns the user who is registered
	 * @return registered user
	 */
	public User getStudent() {
		return student;
	}

	/**
	 * sets the user who is registered
	 * @param student registered user
	 */
	public void setStudent(User student) {
		this.student = student;
	}

	/**
	 * returns the session for which the user is registered
	 * @return registration session
	 */
	public ActivitySession getSession() {
		return session;
	}

	/**
	 * sets the session for which the user is registered
	 * @param session registration session
	 */
	public void setSession(ActivitySession session) {
		this.session = session;
	}
	
	/**
	 * returns the user status entered for this session
	 * @return user status for this session
	 */
	public RegistrationStatus getStatus() {
		return status;
	}
	
	/**
	 * sets the user status for this session
	 * @param status user status
	 */
	public void setStatus(RegistrationStatus status) {
		this.status = status;
	}
	
	/**
	 * returns the notes entered about the user for this session
	 * @return notes about the user
	 */
	public String getNotes() {
		return notes;
	}
	
	/**
	 * sets the notes entered about the user for this session
	 * @param notes notes about the user
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
	 * Returns a string representation of the SessionRegister object
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		StringBuilder strbuilder = new StringBuilder();
		strbuilder.append("Register: ");
		strbuilder.append((getId() == null ? -1 : getId()) + ", ");
		strbuilder.append(student.getId() + ", ");
		strbuilder.append(session.getId() + ", ");
		strbuilder.append("'" + status + "'" + ", ");
		strbuilder.append("'" + notes + "'");
		return strbuilder.toString();
	}
}
