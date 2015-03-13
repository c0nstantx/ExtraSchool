package controllers;

import java.util.List;

import controllers.service.ActivityService;
import play.*;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;
import models.domain.Activity;

public class Activities extends Controller {

	@Transactional
    public static Result showAll() {
    	ActivityService as = new ActivityService();
    	List<Activity> activitiesList = as.findAllActivities();
    	
    	return ok(activities.render(activitiesList));
    }
}
