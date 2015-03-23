package controllers;

import static play.data.Form.form;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.domain.Activity;
import models.domain.Membership;
import models.domain.User;
import models.util.DateLib;
import models.util.Helper;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import controllers.security.AdminSecured;
import controllers.security.Secured;
import controllers.service.ActivityService;
import controllers.service.ActivitySessionService;
import controllers.service.MembershipService;
import controllers.service.UserService;

public class Activities extends Controller {

	protected static final String DATE_FORMAT = "dd-MM-yyyy";
	
	public static class NewActivity {

	    public String name;
	    public String description;
	    public String venue;
	    public String dateStart;
	    public String dateEnd;
	    public String monday;
	    public String tuesday;
	    public String wednesday;
	    public String thursday;
	    public String friday;
	    public String saturday;
	    public String sunday;

		public String validate() {
	    	if (name == "") {
	    		return "Name cannot be blank";
	    	}
	    	if (description == "") {
	    		return "Description name cannot be blank";
	    	}
	    	if (venue == "") {
	    		return "Venue cannot be blank";
	    	}
	    	try {
	    		DateLib.createFromString(DATE_FORMAT, dateStart);
			} catch (Exception e) {
				return "Starting date is not in proper format DD-MM-YYY";
			}
	    	try {
	    		DateLib.createFromString(DATE_FORMAT, dateEnd);
			} catch (Exception e) {
				return "Ending date is not in proper format DD-MM-YYY";
			}
	    	if (monday == null 
	    			&& tuesday == null
	    			&& wednesday == null
	    			&& thursday == null
	    			&& friday == null
	    			&& saturday == null
	    			&& sunday == null
	    			) {
	    		return "You have to select at least one day of the week";
	    	}
	    	return null;
		}
	}
	
	@Security.Authenticated(AdminSecured.class)
	@Transactional
	public static Result create() {
    	UserService us = new UserService();
    	User user = us.findUserByUsername(request().username());
    	
    	Form<NewActivity> activity = form(NewActivity.class);
    	
        return ok(views.html.activities.create.render(activity, user));
	}
	
	@Security.Authenticated(AdminSecured.class)
	@Transactional
	public static Result save() throws ParseException {
    	Form<NewActivity> activityForm = Form.form(NewActivity.class).bindFromRequest();
    	UserService us = new UserService();
    	ActivityService as = new ActivityService();
    	ActivitySessionService ass = new ActivitySessionService();
    	
    	User user = us.findUserByUsername(request().username());
		boolean[] selection = new boolean[] {false, false, false, false, false, false, false};
        if (activityForm.hasErrors()) {
    		selection[0] = false;
//        	System.out.println(selection[0]);
            return badRequest(views.html.activities.create.render(activityForm, user));
        } else {
        	Map<String, String> formData = activityForm.data();
        	if (formData.get("monday") != null) {
        		selection[0] = true;
        	}
        	if (formData.get("tuesday") != null) {
        		selection[1] = true;
        	}
        	if (formData.get("wednesday") != null) {
        		selection[2] = true;
        	}
        	if (formData.get("thursday") != null) {
        		selection[3] = true;
        	}
        	if (formData.get("friday") != null) {
        		selection[4] = true;
        	}
        	if (formData.get("saturday") != null) {
        		selection[5] = true;
        	}
        	if (formData.get("sunday") != null) {
        		selection[6] = true;
        	}
        	Activity newActivity = as.createActivity(
        			formData.get("name"), 
        			formData.get("description"), 
        			formData.get("venue"));
        	if (newActivity != null) {
        		ass.createActivitySessions(
        				newActivity, 
        				DateLib.createFromString(DATE_FORMAT, formData.get("dateStart")), 
        				DateLib.createFromString(DATE_FORMAT, formData.get("dateEnd")), 
        				selection);
            	flash("success", "Activity created successfully");
        	} else {
        		flash("error", "Error creating new activity");
        	}
            return redirect(
  	          routes.Activities.showAll()
            );
        }
	}

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
	
	@Transactional
	@Security.Authenticated(Secured.class)
	public static Result register(Integer id) {
    	UserService us = new UserService();
		ActivityService as = new ActivityService();
		MembershipService ms = new MembershipService();
		Activity activity = as.find(id);
    	User user = us.findUserByUsername(request().username());
    	
    	Membership member = ms.createMembership(activity, user);
    	if (member == null) {
        	flash("error", "You cannot be registered to this activity");
    	} else {
        	flash("success", "Congratulations! You have registered to the activity");
    	}
    	return redirect(routes.Activities.showAll());
		
	}
}
