package models.domain;

import java.util.List;

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
	private List<ActivityCalendar> calendar;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "tutor_id")
	private User tutor;
	
	public Activity() {}
	
	
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

	public List<ActivityCalendar> getCalendar()
	{
		return calendar;
	}

	public void setCalendar(List<ActivityCalendar> calendar)
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
}
