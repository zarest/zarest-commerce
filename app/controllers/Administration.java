package controllers;

import models.Address;
import models.Category;
import models.User;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Validation;
import play.data.validation.ValidationError;
import play.db.ebean.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Security;
import play.twirl.api.Html;
import views.html.admin.adminPanel;
import views.html.admin.createCategory;
import views.html.admin.createUserPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static play.data.Form.form;

/**
 * Created by meysamabl on 10/24/14.
 */
@Security.Authenticated(Secured.class)
public class Administration extends Controller {

    public static Result adminPage() {
        return ok(adminPanel.render(User.findByEmail(request().username())));
    }

    public static Result createUserPage() {
        return ok(createUserPage.render(form(User.class)));
    }

    @Transactional
    public static Result createUser() {
        //DynamicForm requestData = form().bindFromRequest();
        Form<User> userForm = form(User.class).bindFromRequest();
        String retypePassword = userForm.data().get("retypePassword");
        String password = userForm.data().get("password");
        if (userForm.hasErrors()) {
            return badRequest(createUserPage.render(userForm));
        } else {
            if (!retypePassword.isEmpty() && password.compareTo(retypePassword) == 0) {
                User user = userForm.get();
                user.save();
                flash("success", Messages.get("user.create"));
                return redirect(routes.Administration.adminPage());
            } else {
                List<ValidationError> error = new ArrayList<>();
                error.add(new ValidationError("passwordNotMatch", "password.notMatched"));
                userForm.errors().put("passwordNotMatch", error);
                return badRequest(createUserPage.render(userForm));
            }

        }
    }

    public static Result createCategoryPage() {
        return ok(createCategory.render(form(Category.class)));
    }

    public static Result createCategory() {
        Form<Category> categoryForm = form(Category.class).bindFromRequest();
        if (categoryForm.hasErrors()) {
            return badRequest(createCategory.render(categoryForm));
        } else {
            categoryForm.get().save();
            flash("success", Messages.get("category.create"));
            return redirect(routes.Administration.adminPage());
        }
    }
}
