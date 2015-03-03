package models.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@OneToMany(orphanRemoval=true, 
            cascade = CascadeType.ALL, 
            mappedBy = "activity", fetch=FetchType.LAZY)
	private Set<Membership> memberships = new HashSet<Membership>();

    @OneToMany(orphanRemoval=true, 
            cascade = CascadeType.ALL, 
            mappedBy = "activity", fetch=FetchType.LAZY)
	private Set<ActivitySession> sessions = new HashSet<ActivitySession>();
	
	public Activity() {}
	
    public Activity(String name, String description, Date startDate, Date endDate)
	{
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
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
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Set<Membership> getMemberships()
	{
		return memberships;
	}
	
	public void setMemberships(Set<Membership> memberships)
	{
		this.memberships = memberships;
	}
	
	public void addMembership(User user)
	{
		Membership membership = new Membership(this, user, new Date());
		memberships.add(membership);
	}

	public Set<ActivitySession> getSessions()
	{
		return sessions;
	}

	public void setSessions(Set<ActivitySession> sessions)
	{
		this.sessions = sessions;
	}
	
	public void addSession(ActivitySession session) // scheduler check
	{
		session.setActivity(this);
		sessions.add(session);
	}
}
