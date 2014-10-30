package controllers;

import models.Category;
import models.Country;
import models.Customer;
import models.User;
import play.Logger;
import play.data.Form;
import play.data.format.Formatters;
import play.db.ebean.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.adminPanel;
import views.html.admin.createCategory;
import views.html.admin.createCustomerPage;
import views.html.admin.createUserPage;

import java.text.ParseException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import static play.data.Form.form;

/**
 * Created by meysamabl on 10/24/14.
 */
@Security.Authenticated(Secured.class)
public class Administration extends Controller {

    static {
        // add a formater which takes you field and convert it to the proper object
        // this will be called autmaticaly when you call bindFromRequest()

        Formatters.register(Country.class, new Formatters.SimpleFormatter<Country>() {
            @Override
            public Country parse(String input, Locale arg1) throws ParseException {
                // here I extaract It from the DB
                Country country = Secured.findByCountryCode(input);
                return country;
            }

            @Override
            public String print(Country country, Locale arg1) {
                return country.getCountryCode();
            }
        });
    }

    public static Result adminPage() {
        return ok(adminPanel.render(Customer.find.all()));
    }

    public static Result createUserPage() {
        return ok(createUserPage.render(form(User.class)));
    }

    @Transactional
    public static Result createUser() {
        //DynamicForm requestData = form().bindFromRequest();
        Form<User> userForm = form(User.class).bindFromRequest();
        if (userForm.hasErrors()) {
            return badRequest(createUserPage.render(userForm));
        } else {
            User user = userForm.get();
            user.save();
            flash("success", Messages.get("user.create"));
            return redirect(routes.Administration.adminPage());
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

    public static Result createCustomerPage() {
        return ok(createCustomerPage.render(form(Customer.class)));
    }

    @Transactional
    public static Result createCustomer() {

        Form<Customer> customerForm = form(Customer.class).bindFromRequest();
        if (customerForm.hasErrors()) {
            return badRequest(createCustomerPage.render(customerForm));
        } else {
            customerForm.get().save();
            flash("success", Messages.get("customer.create"));
            return redirect(routes.Administration.adminPage());
        }
    }
}
