package controllers;

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
import models.domain.User;

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
}
