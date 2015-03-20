package controllers;

import static play.data.Form.form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import controllers.security.AdminSecured;
import controllers.security.Secured;
import controllers.service.ActivityService;
import controllers.service.UserService;
import play.*;
import play.data.DynamicForm;
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
	
	public static class UserForm {

		public Integer id;
	    public String firstName;
	    public String lastName;
	    public String password;
	    public String passwordRepeat;
	    public String birthDate;
	    public String userType;

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
	    	} else {
	    		return "Password cannot be blank";
	    	}
	    	if (userType == "") {
	    		return "User type cannot be blank";
	    	}
	    	return null;
	    }
	}
	
	@Security.Authenticated(AdminSecured.class)
	@Transactional
	public static Result create() {
    	UserService us = new UserService();
    	User user = us.findUserByUsername(request().username());
    	
    	Form<Profile> profileForm = form(Profile.class);
    	
        return ok(views.html.users.create.render(profileForm, user));
	}
	
	@Security.Authenticated(AdminSecured.class)
	public static Result save() {
        return redirect(
                routes.Users.create()
            );
		
	}
	
	@Transactional
	@Security.Authenticated(AdminSecured.class)
	public static Result show(Integer id) {
    	UserService us = new UserService();
    	User user = us.findUserByUsername(request().username());
    	User userEdit = us.find(id);
    	
    	Form<Profile> userForm = form(Profile.class);
    	userForm.data().put("firstName", userEdit.getFirstName());
    	userForm.data().put("lastName", userEdit.getLastName());
    	userForm.data().put("birthDate", DateLib.format(userEdit.getBirthDate(), "d-M-y"));
    	userForm.data().put("id", userEdit.getId().toString());
    	userForm.data().put("userType", userEdit.getUserType().toString());
    	
        return ok(views.html.users.show.render(userForm, user));
		
	}
	
	@Security.Authenticated(AdminSecured.class)
	@Transactional
	public static Result update(Integer id) {
        return redirect(
                routes.Users.show(id)
            );
	}
	
	@Security.Authenticated(AdminSecured.class)
	@Transactional
	public static Result delete() {
		DynamicForm form = Form.form().bindFromRequest();
		System.out.println(form.get("user_id"));
        return redirect(
                routes.Users.showAll()
            );
	}
	
	@Transactional
	@Security.Authenticated(Secured.class)
    public static Result profile() {
    	UserService us = new UserService();
    	User user = us.findUserByUsername(request().username());
    	
    	Form<Profile> profileForm = form(Profile.class);
    	profileForm.data().put("firstName", user.getFirstName());
    	profileForm.data().put("lastName", user.getLastName());
    	profileForm.data().put("birthDate", DateLib.format(user.getBirthDate(), "d-M-y"));
    	
        return ok(views.html.users.profile.render(profileForm, user));
    }
	
	@Transactional
	@Security.Authenticated(Secured.class)
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
	
	@Transactional
	@Security.Authenticated(AdminSecured.class)
    public static Result showAll() {
    	UserService us = new UserService();
    	List<User> usersList = us.findAllUsers();
    	
    	return ok(views.html.users.users.render(usersList, us.findUserByUsername(request().username())));
    }
}
