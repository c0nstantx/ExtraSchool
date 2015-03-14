package controllers;

import java.util.List;

import controllers.service.ActivityService;
import controllers.service.UserService;
import play.*;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;
import models.domain.Activity;

public class Activities extends Controller {

	@Transactional
	@Security.Authenticated(AdminSecured.class)
    public static Result showAll() {
    	ActivityService as = new ActivityService();
    	UserService us = new UserService();
    	List<Activity> activitiesList = as.findAllActivities();
    	
    	return ok(activities.render(activitiesList, us.findUserByUsername(request().username())));
    }
}
