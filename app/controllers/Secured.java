package controllers;

import play.mvc.*;
import play.mvc.Http.*;

import models.*;

import static play.mvc.Http.Context.*;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.login());
    }

    public static User getCurrentUser() {
        return User.findByEmail(current().request().username());
    }

    // Access rights

}