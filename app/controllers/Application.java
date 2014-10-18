package controllers;

import models.Category;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready.", Category.find.all()));
    }

}
