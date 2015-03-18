package controllers;

import java.util.List;

import controllers.security.Secured;
import controllers.service.ActivityService;
import controllers.service.UserService;
import play.*;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;
import models.domain.Activity;

public class Activities extends Controller {

	@Transactional
	@Security.Authenticated(Secured.class)
    public static Result showAll() {
    	ActivityService as = new ActivityService();
    	UserService us = new UserService();
    	List<Activity> activitiesList = as.findAllActivities();
    	
    	return ok(views.html.activities.activities.render(activitiesList, us.findUserByUsername(request().username())));
    }
}
