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

import models.persistence.RegistrationStatus;
import models.persistence.SessionStatus;
import models.persistence.WeekDay;

import org.hibernate.annotations.Type;

/**
 * ActivitySession Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
@Entity
@Table(name = "activity_sessions")
public class ActivitySession
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "date")
	@Type(type = "date")
	private Date date;
	
	@Column(name = "weekday")
	@Enumerated(EnumType.STRING)
	private WeekDay day;
	
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

	public ActivitySession() {}

	public ActivitySession(Date date)
	{
		this.date = date;
	}

	public ActivitySession(Activity activity, Date date)
	{
		this.activity = activity;
		this.date = date;
	}
	
	public Integer getId()
	{
		return id;
	}

	private void setId(Integer id)
	{
		this.id = id;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public WeekDay getDay() {
		return day;
	}
	
	public void setDay(WeekDay day) {
		this.day = day;
	}
	
	public SessionStatus getStatus() {
		return status;
	}
	
	public void setStatus(SessionStatus status) {
		this.status = status;
	}

	public Activity getActivity()
	{
		return activity;
	}

	public void setActivity(Activity activity)
	{
		this.activity = activity;
	}
	
	public Set<SessionRegister> getRegisters() {
		return registers;
	}
	
	public void setRegisters(Set<SessionRegister> registers) {
		this.registers = registers;
	}
	
	public void addRegister(User user, RegistrationStatus status, String notes) {
		SessionRegister register = new SessionRegister(user, this, status, notes);
		registers.add(register);
	}
}
