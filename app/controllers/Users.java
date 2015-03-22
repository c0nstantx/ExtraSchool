package controllers;

import static play.data.Form.form;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import controllers.security.AdminSecured;
import controllers.security.Secured;
import controllers.service.UserService;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;
import models.domain.User;
import models.domain.UserType;
import models.util.DateLib;

public class Users extends Controller {

	protected static final String DATE_FORMAT = "dd-MM-yyyy";
	
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
	    		DateLib.createFromString(DATE_FORMAT, birthDate);
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
	    		DateLib.createFromString(DATE_FORMAT, birthDate);
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
	    	if (userType == "") {
	    		return "User type cannot be blank";
	    	}
	    	return null;
	    }
	}
	
	public static class NewUser {

	    public String username;
	    public String firstName;
	    public String lastName;
	    public String password;
	    public String passwordRepeat;
	    public String birthDate;
	    public String userType;

		public String validate() {
	    	if (username == "") {
	    		return "Username cannot be blank";
	    	}
	    	if (firstName == "") {
	    		return "First name cannot be blank";
	    	}
	    	if (lastName == "") {
	    		return "Last name cannot be blank";
	    	}
	    	try {
	    		DateLib.createFromString(DATE_FORMAT, birthDate);
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
    	
    	Form<NewUser> newUser = form(NewUser.class);
    	
        return ok(views.html.users.create.render(newUser, user));
	}
	
	@Security.Authenticated(AdminSecured.class)
	@Transactional
	public static Result save() throws ParseException {
    	Form<NewUser> userForm = Form.form(NewUser.class).bindFromRequest();
    	UserService us = new UserService();
    	
    	User user = us.findUserByUsername(request().username());
        if (userForm.hasErrors()) {
            return badRequest(views.html.users.create.render(userForm, user));
        } else {
        	Map<String, String> formData = userForm.data();
        	UserType uType = UserType.Student;
        	if (formData.get("userType").equals("Admin")) {
				uType = UserType.Admin;
        	} else if (formData.get("userType").equals("Tutor")) {
				uType = UserType.Tutor;
        	}
        	us.createUser(formData.get("username"), formData.get("password"), uType, 
        			formData.get("firstName"), formData.get("lastName"), 
        			DateLib.createFromString(DATE_FORMAT, formData.get("birthDate")));
        	flash("success", "User created successfully");
            return redirect(
  	          routes.Users.showAll()
            );
        }
	}
	
	@Transactional
	@Security.Authenticated(AdminSecured.class)
	public static Result show(Integer id) {
    	UserService us = new UserService();
    	User user = us.findUserByUsername(request().username());
    	User userEdit = us.find(id);
    	
    	Form<UserForm> userForm = form(UserForm.class);
    	userForm.data().put("firstName", userEdit.getFirstName());
    	userForm.data().put("lastName", userEdit.getLastName());
    	userForm.data().put("birthDate", DateLib.format(userEdit.getBirthDate(), DATE_FORMAT));
    	userForm.data().put("userType", userEdit.getUserType().toString());
    	
        return ok(views.html.users.show.render(userForm, user, id));
		
	}
	
	@Security.Authenticated(AdminSecured.class)
	@Transactional
	public static Result update(Integer id) {
    	Form<UserForm> userForm = Form.form(UserForm.class).bindFromRequest();
    	UserService us = new UserService();
    	
    	User user = us.findUserByUsername(request().username());
    	User userEdit = us.find(id);
        if (userForm.hasErrors()) {
            return badRequest(views.html.users.show.render(userForm, user, id));
        } else {
        	Map<String, String> formData = userForm.data();
        	userEdit.setLastName(formData.get("firstName"));
        	userEdit.setFirstName(formData.get("lastName"));
        	UserType uType = UserType.Student;
        	if (formData.get("userType").equals("Admin")) {
				uType = UserType.Admin;
        	} else if (formData.get("userType").equals("Tutor")) {
				uType = UserType.Tutor;
        	}
        	userEdit.setUserType(uType);
        	try {
        		userEdit.setBirthDate(DateLib.createFromString(DATE_FORMAT, formData.get("birthDate")));
			} catch (Exception e) {
				// TODO: handle exception
			}
        	if (formData.get("password") != "") {
        		userEdit.setPassword(formData.get("password"));
        	}
        	us.updateUser(userEdit);
        	flash("success", "User updated successfully");
            return redirect(
                routes.Users.show(id)
            );
        }
	}
	
	@Security.Authenticated(AdminSecured.class)
	@Transactional
	public static Result delete() {
		DynamicForm form = Form.form().bindFromRequest();
		Integer userId = Integer.parseInt(form.get("user_id"));
		UserService us = new UserService();
		User user = us.find(userId);
		if (us.deleteUser(user)) {
			flash("success", "User deleted successfully");
		} else {
			flash("error", "Error deleting user. The user is member of an activity");
		}
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
    	profileForm.data().put("birthDate", DateLib.format(user.getBirthDate(), DATE_FORMAT));
    	
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
            	user.setBirthDate(DateLib.createFromString(DATE_FORMAT, formData.get("birthDate")));
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
