package controllers;

import models.Category;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Lang;
import play.mvc.*;

import views.html.*;

@With(CategoryMenu.class)
public class Application extends Controller {


    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Lang getCurrentLang() {
        return Http.Context.current().lang();
    }

    public static Result changeLocale() {
        DynamicForm requestData = Form.form().bindFromRequest();
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
        return ok(aboutUs.render());
    }

    public static Result contactUs()  {
        return ok(contactUs.render());
    }
}
