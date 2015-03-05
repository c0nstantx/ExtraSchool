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

import models.persistence.RegistrationStatus;

/**
 * SessionRegister Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
@Entity
@Table(name = "session_registers")
public class SessionRegister
{
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

	public SessionRegister() {}
	
	public SessionRegister(User student, ActivitySession session, RegistrationStatus status, String notes)
	{
		this.student = student;
		this.session = session;
		this.status = status;
		this.notes = notes;
	}
	
	public Integer getId()
	{
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(Integer id)
	{
		this.id = id;
	}

	public User getStudent()
	{
		return student;
	}

	public void setStudent(User student)
	{
		this.student = student;
	}

	public ActivitySession getSession()
	{
		return session;
	}

	public void setSession(ActivitySession session)
	{
		this.session = session;
	}
	
	public RegistrationStatus getStatus() {
		return status;
	}
	
	public void setStatus(RegistrationStatus status) {
		this.status = status;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
