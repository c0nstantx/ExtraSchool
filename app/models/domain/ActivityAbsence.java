package models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * ActivityAbsence Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
@Entity
@Table(name = "activity_absence")
public class ActivityAbsence
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToOne()
	@JoinColumn(name = "user_id")
	private User student;
	
	@OneToOne()
	@JoinColumn(name = "activity_calendar_id")
	private ActivityCalendar activityCalendar;

	public ActivityAbsence() {}
	
	public ActivityAbsence(User student, ActivityCalendar activityCalendar)
	{
		super();
		this.student = student;
		this.activityCalendar = activityCalendar;
	}

	public Integer getId()
	{
		return id;
	}

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

	public ActivityCalendar getActivityCalendar()
	{
		return activityCalendar;
	}

	public void setActivityCalendar(ActivityCalendar activityCalendar)
	{
		this.activityCalendar = activityCalendar;
	}
}
