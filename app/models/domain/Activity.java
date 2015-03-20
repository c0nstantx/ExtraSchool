package models.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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

import models.util.DateLib;

/**
 * Activity Entity
 * Describes the activities that a student can participate
 * 
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
@Entity
@Table(name="activities")
public class Activity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;
	
	@Column(name = "venue")
	private String venue;
	
	@OneToMany(orphanRemoval=true, 
            cascade = CascadeType.ALL, 
            mappedBy = "activity", fetch=FetchType.LAZY)
	private Set<Membership> memberships = new HashSet<Membership>();

    @OneToMany(orphanRemoval=true, 
            cascade = CascadeType.ALL, 
            mappedBy = "activity", fetch=FetchType.LAZY)
	private Set<ActivitySession> sessions = new HashSet<ActivitySession>();
	
    /**
	 * Default constructor
	 */
	public Activity() {}
	
	/**
	 * Constructor
	 * @param name the name of the activity
	 * @param description a short description of the activity
	 * @param venue the venue where the activity sessions take place
	 */
    public Activity(String name, String description, String venue) {
		this.name = name;
		this.description = description;
		this.venue = venue;
	}

    /**
	 * returns the id of the activity
	 * @return activity id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * sets the id of the activity
	 * @param id activity id
	 */
	@SuppressWarnings("unused")
	private void setId(Integer id) {
		this.id = id;
	}

	/**
	 * returns the name of the activity
	 * @return activity name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the activity
	 * @param name activity name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * returns the description of the activity
	 * @return activity description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * sets the description of the activity
	 * @param description activity description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * returns the venue where the activity sessions take place
	 * @return activity venue
	 */
	public String getVenue() {
		return venue;
	}
	
	/**
	 * sets the venue where the activity sessions take place
	 * @param venue activity venue
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}
	
	/**
	 * returns the set of memberships linked to the activity
	 * @return set of activity memberships
	 */
	public Set<Membership> getMemberships() {
		return memberships;
	}
	
	/**
	 * sets the set of memberships linked to the activity
	 * @param memberships set of memberships
	 */
	public void setMemberships(Set<Membership> memberships) {
		this.memberships = memberships;
	}
	
	/**
	 * adds a new membership to the set of memberships
	 * @param membership new membership
	 */
	public void addMembership(Membership membership) {
		memberships.add(membership);
	}

	/**
	 * returns the set of activity sessions
	 * @return set of activity sessions
	 */
	public Set<ActivitySession> getSessions() {
		return sessions;
	}

	/**
	 * sets the set of activity sessions
	 * @param sessions set of sessions
	 */
	public void setSessions(Set<ActivitySession> sessions) {
		this.sessions = sessions;
	}
	
	/**
	 * adds a new session to the set of sessions
	 * @param session new activity session
	 */
	public void addSession(ActivitySession session) {
		session.setActivity(this);
		sessions.add(session);
	}
	
	/**
	 * Creates the set of all activity sessions between (and including) two dates, for the specified week days only
	 * @param startDate starting date for activity sessions
	 * @param endDate closing date for activity sessions
	 * @param selection a boolean array of 7 places, indicating the days of the week when activity sessions are scheduled
	 */
	public void createSessions(Date startDate, Date endDate, boolean[] selection) {
		while (!DateLib.succeeds(startDate, endDate)) {
			if (selection[DateLib.getWeekday(startDate) - 1]) {
				this.addSession(new ActivitySession(DateLib.copyDateObject(startDate)));
			}
			DateLib.addDays(startDate, 1);
		}
	}
	
	/**
	 * Returns a string representation of the Activity object
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		StringBuilder strbuilder = new StringBuilder();
		strbuilder.append("Activity: ");
		strbuilder.append((getId() == null ? -1 : getId()) + ", ");
		strbuilder.append("'" + getName() + "'" + ", ");
		strbuilder.append("'" + getDescription() + "'" + ", ");
		strbuilder.append("'" + getVenue() + "'" + ", ");
		strbuilder.append(getMemberships().size() + " membership(s)" + ", ");
		strbuilder.append(getSessions().size() + " session(s)");
		return strbuilder.toString();
	}

	/**
	 * Returns if an activity is currently active or not
	 * @return boolean
	 */
	public boolean isActive() {
		Set<ActivitySession> sessions = getSessions();
		if (sessions.size() > 0) {
			ActivitySession firstSession = sessions.iterator().next();
			ActivitySession lastSession = sessions.iterator().next();
			for (Iterator<ActivitySession> iterator = sessions.iterator(); iterator.hasNext();) {
				ActivitySession session = (ActivitySession) iterator.next();
				if (session.getDate().before(firstSession.getDate())) {
					firstSession = session;
				}
				if (session.getDate().after(lastSession.getDate())) {
					lastSession = session;
				}
			}
			Date today = new Date();
			return today.after(firstSession.getDate()) && today.before(lastSession.getDate());
		}
		return false;
	}
}
