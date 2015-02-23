package models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ActivityStudent Entity
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
@Entity
@Table(name = "activity_students")
public class ActivityStudent
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne()
	@JoinColumn(name = "activity_id")
	private Activity activity;

	@ManyToOne()
	@JoinColumn(name = "student_id")
	private User student;
	
	public ActivityStudent() {}

	public ActivityStudent(Activity activity, User student)
	{
		super();
		this.activity = activity;
		this.student = student;
	}
	
	
}
