package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Security;

/**
 * Created by meysamabl on 10/24/14.
 */
@Security.Authenticated(Secured.class)
public class Administration extends Controller {

    public static Result adminPage() {
        return Results.TODO;
    }

}
