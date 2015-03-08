package models.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import models.util.DateLib;

import org.hibernate.annotations.Type;

/**
 * Report Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
@Embeddable
public class Report {
	@Column(name = "published")
	private boolean published = false;
	
	@Column(name = "publication_date")
	@Type(type = "date")
	private Date publicationDate;
	
	@Column(name = "grade")
	private double grade;
	
	@Column(name = "comment")
	private String comment;
	
	/**
	 * Default constructor
	 */
	public Report() {}
	
	/**
	 * returns the publication state of the report
	 * @return report publication state
	 */
	public boolean getPublished() {
		return published;
	}
	
	/**
	 * sets the publication state of the report
	 * @param published report publication state
	 */
	public void setPublished(boolean published) {
		this.published = published;
	}
	
	/**
	 * returns the publication date of the report
	 * @return report publication state
	 */
	public Date getPublicationDate() {
		return publicationDate;
	}
	
	/**
	 * sets the publication date of the report
	 * @param publicationDate report publication state
	 */
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	/**
	 * returns the grade of the report
	 * @return report grade
	 */
	public double getGrade() {
		return grade;
	}
	
	/**
	 * sets the grade of the report
	 * @param grade report grade
	 */
	public void setGrade(double grade) {
		this.grade = grade;
	}
	
	/**
	 * returns the comment of the report
	 * @return report comment
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * sets the comment of the report
	 * @param comment report comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Returns a string representation of the Report object
	 * @return a string representation of the object
	 */
	public String toString() {
		StringBuilder strbuilder = new StringBuilder();
		strbuilder.append(getPublished() + ", ");
		strbuilder.append(DateLib.dateAsString(getPublicationDate()) + ", ");
		strbuilder.append(getGrade() + ", ");
		strbuilder.append("'" + getComment() + "'");
		return strbuilder.toString();
	}
}
