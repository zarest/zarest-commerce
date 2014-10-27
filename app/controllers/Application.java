package controllers;

import models.Category;
import models.User;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Lang;
import play.i18n.Messages;
import play.mvc.*;

import views.html.*;

import static play.data.Form.form;

@With(CategoryMenu.class)
public class Application extends Controller {


    public static Result index() {
        return ok(index.render("home"));
    }

    public static Lang getCurrentLang() {
        return Http.Context.current().lang();
    }

    public static Result changeLocale() {
        DynamicForm requestData = form().bindFromRequest();
        String locale = requestData.get("locale");
        //Logger.info("My Locale: " + locale);
        changeLang(locale);
        if (request().hasHeader(REFERER)) {
            String refererURL = request().getHeader(REFERER);
            //Logger.info("My URL: " + refererURL);
            return redirect(refererURL);
        } else {
            //Logger.info("GOING BACK HOME: " + routes.Application.index().absoluteURL(request()));
            return redirect(routes.Application.index().absoluteURL(request()));
        }

    }

    public static Result aboutUsPage() {
        return ok(aboutUs.render("aboutUs"));
    }

    public static Result contactUs() {
        return ok(contactUs.render("contactUs"));
    }

    public static Result productPage() {
        return ok(product.render("products"));
    }

    // -- Authentication

    public static class Login {

        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, password) == null) {
                return Messages.get("login.invalid");
            } else if (User.adminAuthenticate(email, password) == false) {
                return Messages.get("login.unauthorized");
            }
            return null;
        }

    }

    /**
     * Login page.
     */
    public static Result login() {
        return ok(
                login.render(form(Login.class))
        );
    }

    /**
     * Handle login form submission.
     */
    public static Result authenticate() {

        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("email", loginForm.get().email);
            return redirect(
                    routes.Administration.adminPage()
            );
        }
    }

    /**
     * Logout and clean the session.
     */
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.login()
        );
    }
}
