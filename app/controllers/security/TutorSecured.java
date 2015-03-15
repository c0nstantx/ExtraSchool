package controllers.security;

import controllers.service.UserService;
import models.domain.User;
import models.domain.UserType;
import play.mvc.Http.*;

public class TutorSecured extends Secured {

    @Override
    public String getUsername(Context ctx) {
        String username = ctx.session().get("username");
    	UserService us = new UserService();
    	User user = us.findUserByUsername(username);
        if (username != null && isTutor(user)) {
        	return username;
        }
        ctx.flash().put("error", "Only Tutors can access that page.");
        return null;
    }
    
    private boolean isTutor(User user) {
    	UserType userType = user.getUserType();
    	if (user != null && (userType == UserType.Tutor || userType == UserType.Admin)) {
    		return true;
    	}
    	return false;
    	
    }
}