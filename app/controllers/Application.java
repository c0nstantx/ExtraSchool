package controllers;

import static play.data.Form.form;
import models.domain.User;
import models.util.Authenticator;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;
import views.html.login;
import controllers.security.Secured;
import controllers.service.UserService;

public class Application extends Controller {

	public static class Login {

	    public String username;
	    public String password;

	    public String validate() {
	    	if (username == "") {
	    		return "Username cannot be blank";
	    	}
	    	if (password == "") {
	    		return "Password cannot be blank";
	    	}
	    	User user = Authenticator.authenticate(username, password);
	        if (user == null) {
	          return "Invalid username or password";
	        }
	        return null;
	    }
	}
	
	@Security.Authenticated(Secured.class)
	@Transactional
    public static Result index() {
		UserService us = new UserService();
        return ok(index.render(us.findUserByUsername(request().username())));
    }

    public static Result login() {
        return ok(login.render(form(Login.class)));
    }

    public static Result logout() {
    	session().clear();
    	flash("success", "You have logged out. Thank you for using ExtraSchool");
    	return redirect(routes.Application.login());
    }
    
    @Transactional
    public static Result authenticate() {
    	Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("username", loginForm.get().username);
            return redirect(
                routes.Application.index()
            );
        }
    }
}
