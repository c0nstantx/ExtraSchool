package controllers;

import static play.data.Form.form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import controllers.security.Secured;
import controllers.service.ActivityService;
import controllers.service.UserService;
import play.*;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;
import models.domain.Activity;
import models.domain.User;
import models.util.Authenticator;
import models.util.DateLib;

public class Users extends Controller {
	
	public static class Profile {

	    public String firstName;
	    public String lastName;
	    public String password;
	    public String passwordRepeat;
	    public String birthDate;

	    public String validate() {
	    	if (firstName == "") {
	    		return "First name cannot be blank";
	    	}
	    	if (lastName == "") {
	    		return "Last name cannot be blank";
	    	}
	    	try {
	    		DateLib.createFromString("d-M-y", birthDate);
			} catch (Exception e) {
				return "Birth date is not in proper format DD-MM-YYY";
			}
	    	if (password != "") {
	    		if (passwordRepeat == "") {
	    			return "Password repeat cannot be blank";
	    		}
	    		if (!password.equals(passwordRepeat)) {
	    			return "Password repeat must be the same as password";
	    		}
	    	}
	    	return null;
	    }
	}
	

	@Transactional
	@Security.Authenticated(Secured.class)
    public static Result profile() {
    	ActivityService as = new ActivityService();
    	UserService us = new UserService();
    	User user = us.findUserByUsername(request().username());
    	
    	Form<Profile> profileForm = form(Profile.class);
    	profileForm.data().put("firstName", user.getFirstName());
    	profileForm.data().put("lastName", user.getLastName());
    	profileForm.data().put("birthDate", DateLib.format(user.getBirthDate(), "d-M-y"));
    	
        return ok(views.html.users.profile.render(profileForm, user));
    }
	
	@Security.Authenticated(Secured.class)
	@Transactional
	public static Result updateProfile() {
    	Form<Profile> profileForm = Form.form(Profile.class).bindFromRequest();
    	UserService us = new UserService();
    	
    	User user = us.findUserByUsername(request().username());
        if (profileForm.hasErrors()) {
            return badRequest(views.html.users.profile.render(profileForm, user));
        } else {
        	Map<String, String> formData = profileForm.data();
        	user.setLastName(formData.get("firstName"));
        	user.setFirstName(formData.get("lastName"));
        	try {
            	user.setBirthDate(DateLib.createFromString("d-M-y", formData.get("birthDate")));
			} catch (Exception e) {
				// TODO: handle exception
			}
        	if (formData.get("password") != "") {
        		user.setPassword(formData.get("password"));
        	}
        	us.updateUser(user);
        	flash("success", "Your profile has been updated");
            return redirect(
                routes.Users.profile()
            );
        }
	}
}
