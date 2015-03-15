package controllers.security;

import controllers.routes;
import play.mvc.*;
import play.mvc.Http.*;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("username");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
    	if (ctx.session().get("username") != null) {
            return redirect(routes.Application.index());
    	}
        return redirect(routes.Application.login());
    }
}