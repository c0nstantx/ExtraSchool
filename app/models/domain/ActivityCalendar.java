package models.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * ActivityCalendar Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
@Entity
@Table(name = "activity_calendar")
public class ActivityCalendar
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "date")
	@Type(type = "date")
	private Date date;

	@ManyToOne()
	@JoinColumn(name = "activity_id")
	private Activity activity;

	public ActivityCalendar() {}

	public ActivityCalendar(Date date)
	{
		this.date = date;
	}

	public ActivityCalendar(Activity activity, Date date)
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

	public Activity getActivity()
	{
		return activity;
	}

	public void setActivity(Activity activity)
	{
		this.activity = activity;
	}
	
}
