package models.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.HashSet;

/**
 * Activity Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
@Entity
@Table(name="activities")
public class Activity
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

    @OneToMany(orphanRemoval=true, 
            cascade = CascadeType.ALL, 
            mappedBy = "activity", fetch=FetchType.LAZY)
	private Set<ActivityCalendar> calendar = new HashSet<ActivityCalendar>();

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "tutor_id")
	private User tutor;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "activity")
	private Set<ActivityStudent> students = new HashSet<ActivityStudent>();
	
	public Activity() {}
	
    public Activity(String name, String description)
	{
		this.name = name;
		this.description = description;
	}
	
    public Activity(String name, String description,
			Set<ActivityCalendar> calendar, User tutor)
	{
		super();
		this.name = name;
		this.description = description;
		this.calendar = calendar;
		this.tutor = tutor;
	}


	public Integer getId()
	{
		return id;
	}

	private void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Set<ActivityCalendar> getCalendar()
	{
		return calendar;
	}

	public void setCalendar(Set<ActivityCalendar> calendar)
	{
		this.calendar = calendar;
	}

	public User getTutor()
	{
		return tutor;
	}

	public void setTutor(User tutor)
	{
		this.tutor = tutor;
	}
	
	public void addActivityCalendar(ActivityCalendar ac)
	{
		this.calendar.add(ac);
		ac.setActivity(this);
	}
	
	public void addStudent(User student)
	{
		ActivityStudent as = new ActivityStudent(this, student);
		this.students.add(as);
	}

	public Set<ActivityStudent> getStudents()
	{
		return students;
	}

	public void setStudents(Set<ActivityStudent> students)
	{
		this.students = students;
	}
}
