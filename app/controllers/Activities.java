package controllers;

import java.util.Date;
import java.util.List;

import controllers.security.AdminSecured;
import controllers.security.Secured;
import controllers.service.ActivityService;
import controllers.service.UserService;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;
import models.domain.Activity;
import models.domain.ActivitySession;
import models.domain.User;
import models.util.Helper;

public class Activities extends Controller {

	@Transactional
	@Security.Authenticated(Secured.class)
    public static Result showAll() {
    	ActivityService as = new ActivityService();
    	UserService us = new UserService();
    	List<Activity> activitiesList = as.findAllActivities();
    	
    	return ok(views.html.activities.activities.render(activitiesList, us.findUserByUsername(request().username())));
    }
	
	@Security.Authenticated(AdminSecured.class)
	@Transactional
	public static Result delete() {
		DynamicForm form = Form.form().bindFromRequest();
		Integer activityId = Integer.parseInt(form.get("activity_id"));
		ActivityService as = new ActivityService();
		Activity activity = as.find(activityId);
		if (as.deleteActivity(activity)) {
			flash("success", "Activity deleted successfully");
		} else {
			flash("error", "Error deleting activity. There are signed users to that activity");
		}
        return redirect(
                routes.Activities.showAll()
            );
	}
	
	@Transactional
	@Security.Authenticated(Secured.class)
	public static Result show(Integer id) {
    	UserService us = new UserService();
		ActivityService as = new ActivityService();
		Activity activity = as.find(id);

		List<Date> dates = Helper.getActivityDates(activity);
    	User user = us.findUserByUsername(request().username());
    	
        return ok(views.html.activities.show.render(activity, user, dates));
		
	}
}
