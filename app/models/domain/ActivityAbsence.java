package models.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * ActivityAbsence Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
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
