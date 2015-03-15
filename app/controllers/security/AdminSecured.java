package controllers.security;

import models.domain.User;
import models.domain.UserType;
import controllers.service.UserService;
import play.mvc.*;
import play.mvc.Http.*;

public class AdminSecured extends Secured {

    @Override
    public String getUsername(Context ctx) {
        String username = ctx.session().get("username");
    	UserService us = new UserService();
    	User user = us.findUserByUsername(username);
        if (username != null && isTutor(user)) {
        	return username;
        }
        ctx.flash().put("error", "Only Administrators can access that page.");
        return null;
    }
    
    private boolean isTutor(User user) {
    	UserType userType = user.getUserType();
    	if (user != null && userType == UserType.Admin) {
    		return true;
    	}
    	return false;
    	
    }}