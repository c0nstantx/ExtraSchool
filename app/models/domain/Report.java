package models.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Report Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
@Embeddable
public class Report {
	@Column(name = "published")
	private boolean published = false;
	
	@Column(name = "publication_date")
	private Date publicationDate;
	
	@Column(name = "grade")
	private double grade;
	
	@Column(name = "comment")
	private String comment;
	
	public Report() {}
	
	public boolean getPublished() {
		return published;
	}
	
	public void setPublished(boolean published) {
		this.published = published;
	}
	
	public Date getPublicationDate() {
		return publicationDate;
	}
	
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	public double getGrade() {
		return grade;
	}
	
	public void setGrade(double grade) {
		this.grade = grade;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
}
