package controllers;

import models.*;
import play.Logger;
import play.data.Form;
import play.data.format.Formatters;
import play.data.validation.ValidationError;
import play.db.ebean.Transactional;
import play.i18n.Messages;
import play.mvc.*;
import views.html.admin.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;


import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static play.data.Form.form;

/**
 * Created by meysamabl on 10/24/14.
 */
@Security.Authenticated(Secured.class)
public class Administration extends Controller {

    //----------PAGES--------------------//

    public static Result adminPage() {
        return ok(adminPanel.render(Category.findSuperParentCategories()));
    }

    public static Result createUserPage() {
        return ok(createUserPage.render(form(User.class)));
    }

    public static Result createCategoryPage() {
        return ok(createCategoryPage.render(form(Category.class)));
    }

    public static Result createCustomerPage() {
        return ok(createCustomerPage.render(form(Customer.class)));
    }

    //-----------FORM SAVE---------------//

    @Transactional
    public static Result createUser() {
        //DynamicForm requestData = form().bindFromRequest();
        Form<User> userForm = form(User.class).bindFromRequest();
        if (userForm.hasErrors()) {
            return badRequest(createUserPage.render(userForm));
        } else {
            try {
                userForm.get().save();
            } catch (Exception ex) {
                Logger.error(ex.getMessage());
                return badRequest(createUserPage.render(userForm));
            }
            flash("success", Messages.get("user.create"));
            return redirect(routes.Administration.adminPage());
        }
    }

    public static Result getImage(Long id) {
        Category cat = Category.find.byId(id);
        try {
            return ok(cat.picture).as("text/image");
        } catch (Exception f) {
            return badRequest("Bad File...");
        }
    }


    @Transactional
    public static Result createCustomer() {

        Form<Customer> customerForm = form(Customer.class).bindFromRequest();
        if (customerForm.hasErrors()) {
            return badRequest(createCustomerPage.render(customerForm));
        } else {
            try {
                customerForm.get().save();
            } catch (Exception ex) {
                Logger.error(ex.getMessage());
                return badRequest(createCustomerPage.render(customerForm));
            }
            flash("success", Messages.get("customer.create"));
            return redirect(routes.Administration.adminPage());
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.MultipartFormData.class)
    public static Result createCategory() {
        Form<Category> categoryForm = form(Category.class).bindFromRequest();
        if (categoryForm.hasErrors()) {
            for (Map.Entry<String, List<ValidationError>> err : categoryForm.errors().entrySet()) {
                Logger.info("Error: " + err);
            }
            return badRequest(createCategoryPage.render(categoryForm));
        } else {
            try {
                Category cat = categoryForm.get();
                Http.MultipartFormData body = request().body().asMultipartFormData();
                Http.MultipartFormData.FilePart picture = body.getFile("picture");
                if (picture != null) {
                    String fileName = picture.getFilename();
                    Logger.info("filename: " + fileName);
                    String contentType = picture.getContentType();
                    Logger.info("contentType: " + contentType);
                    File file = picture.getFile();

                    Path path = Paths.get(file.getPath());
                    Logger.info("Path: " + file.getPath());
                    byte[] data = Files.readAllBytes(path);
                    cat.picture = data;

                }
                cat.save();
                flash("success", Messages.get("category.create"));
                return redirect(routes.Administration.adminPage());

            } catch (Exception ex) {
                Logger.error(ex.getMessage());
                return badRequest(createCategoryPage.render(categoryForm));
            }
        }
    }

    public static Result upload() {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart picture = body.getFile("picture");
        if (picture != null) {
            String fileName = picture.getFilename();
            String contentType = picture.getContentType();
            File file = picture.getFile();
            Logger.info("File Path: " + file.getAbsolutePath() + " " + file.toPath().toString());
            return ok("File uploaded");
        } else {
            flash("error", "Missing file");
            return redirect(routes.Application.index());
        }
    }

    public static Result createProductPage() {
        return ok(createProductPage.render(form(Product.class)));
    }

    public static Result createProduct() {
        return Results.TODO;
    }
}
