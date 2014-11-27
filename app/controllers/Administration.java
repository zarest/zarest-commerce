package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import org.apache.commons.io.FileUtils;
import play.Logger;
import play.data.Form;
import play.data.validation.ValidationError;
import play.db.ebean.Transactional;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static play.data.Form.form;

/**
 * Created by meysamabl on 10/24/14.
 */
@Security.Authenticated(Secured.class)
public class Administration extends Controller {


    //----------PAGES--------------------//

    public static Result adminPage() {
        return ok(adminPanel.render(Product.find.all()));
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

    public static Result createProductPage() {
        if (Supplier.find.findRowCount() > 0) {
            return ok(createProductPage.render(form(Product.class)));
        } else {
            flash("error", Messages.get("error.noSupplier"));
            return redirect(routes.Administration.adminPage());
        }
    }

    public static Result createSupplierPage() {
        return ok(createSupplierPage.render(form(Supplier.class)));
    }

//    public static Result getImage(Long id) {
//        Category cat = Category.find.byId(id);
//        try {
//            return ok(cat.picture).as("text/image");
//        } catch (Exception f) {
//            return badRequest("Bad File...");
//        }
//    }

    public static Result getSubCategories(Long id) {
        return ok(Json.toJson(Category.subCategoryOptions(id)));
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
    public static Result createCategory() {
        Form<Category> categoryForm = form(Category.class).bindFromRequest();
        if (categoryForm.hasErrors()) {
            play.Logger.debug("Error: " + categoryForm.errorsAsJson());
            return badRequest(createCategoryPage.render(categoryForm));
        } else {
            try {
                Category cat = categoryForm.get();
                cat.name = cat.parentCategory != null ?
                        cat.parentCategory.name + "_" + cat.name : cat.name;
                Logger.debug("Category Name: {}", cat.name);
                Http.MultipartFormData body = request().body().asMultipartFormData();
                Http.MultipartFormData.FilePart picture = body.getFile("picture");
                //play.Logger.debug("filepart: " + picture);
                if (picture != null) {
                    if (Image.ImageType.get(picture.getContentType()) == null) {
                        play.Logger.debug("File: " + picture);
                        return badRequest(createCategoryPage.render(categoryForm));
                    }
                    File newFile = null;
                    String path;
                    if (cat.parentCategory == null) {
                        path = "public/images/parentCategories";
                    } else {
                        path = "public/images/subCategories";
                    }
                    try {
                        newFile = saveImage(picture, path, cat.name);
                    } catch (Exception e) {
                        play.Logger.error("Error on saving Image: " + e.getMessage());
                        return badRequest(createCategoryPage.render(categoryForm));
                    }

                    String filePath = path.substring(7) + "/" + newFile.getName();
                    play.Logger.debug("File Path: " + filePath);
                    cat.imagePath = filePath;
//                    File file = picture.getFile();
//
//                    Path path = Paths.get(file.getPath());
//                    Logger.debug("Path: " + file.getPath());
//                    byte[] data = Files.readAllBytes(path);

                }
                cat.save();
                flash("success", Messages.get("category.create"));
                return redirect(routes.Administration.adminPage());

            } catch (Exception ex) {
                Logger.debug(ex.getMessage());
                return badRequest(createCategoryPage.render(categoryForm));
            }
        }
    }

    @Transactional
    public static Result createProduct() {

        Form<Product> productForm = form(Product.class).bindFromRequest();
        if (productForm.hasErrors()) {
            play.Logger.error(productForm.errorsAsJson().toString());
            return badRequest(createProductPage.render(productForm));
        } else {
            MultipartFormData body = request().body().asMultipartFormData();
            Product product = productForm.get();
            for (int i = 0; i < 4; i++) {
                FilePart picture = body.getFile("pictures[" + i + "]");
                //play.Logger.debug("File: " + picture);
                if (picture != null) {
                    if (Image.ImageType.get(picture.getContentType()) == null) {
                        play.Logger.debug("File: " + picture);
                        return badRequest(createProductPage.render(productForm));
                    }
                    File newFile = null;
                    try {
                        newFile = saveImage(picture, "public/images/upload", null);
                    } catch (Exception e) {
                        play.Logger.error("Error on saving Image: " + e.getMessage());
                        return badRequest(createProductPage.render(productForm));
                    }
                    Image image = new Image();
                    image.pic = newFile;
                    image.filePath = "images/upload/" + newFile.getName();
                    play.Logger.debug("File Path: " + newFile.getPath());
                    product.images.add(image);
                }
            }
            if (product.images.isEmpty()) {
                Logger.error("Error: image is empty");
                productForm.errors().put("image.required",
                        Arrays.asList(
                                new ValidationError("image.required",
                                        Messages.get("image.required"))));
                Logger.error("Error: " + productForm.errorsAsJson());
                return badRequest(createProductPage.render(productForm));
            }
            product.save();
            flash("success", Messages.get("product.create"));
            return redirect(routes.Administration.adminPage());
        }
    }

    private static File saveImage(FilePart filePart, String filePath, String nameOfFile) throws Exception {
        try {
            String fileName = filePart.getFilename();
            play.Logger.debug("FileName: " + fileName);
            String extension = fileName.substring(fileName.indexOf("."));
            play.Logger.debug("Extention: " + extension);
            if (nameOfFile != null) {
                fileName = nameOfFile + extension;
            } else {
                String uuid = uuid = java.util.UUID.randomUUID().toString();
                fileName = uuid + extension;
            }

            play.Logger.debug("New FileName: " + fileName);

            File newFile = new File(filePath, fileName);
            File file = filePart.getFile();
            FileUtils.moveFile(file, newFile);

            play.Logger.debug("File moved");
            return newFile;
        } catch (IOException ioe) {
            play.Logger.debug("Error : " + ioe.getLocalizedMessage());
            throw new Exception(ioe);
        }
    }

    // For Uploading the Pictures ------------------------------------
    public static Result upload() {
        MultipartFormData body = request().body().asMultipartFormData();

        FilePart picture = body.getFile("picture");
        play.Logger.debug("File: " + body);
        if (picture != null) {
            String fileName = picture.getFilename();
            String extension = fileName.substring(fileName.indexOf("."));
            String uuid = uuid = java.util.UUID.randomUUID().toString();
            fileName = uuid + extension;
            play.Logger.debug("Image: " + fileName);

            String contentType = picture.getContentType();
            File file = picture.getFile();
            try {
                File newFile = new File("public/images/upload", fileName);
                FileUtils.moveFile(file, newFile);
                play.Logger.debug("File moved");
            } catch (IOException ioe) {
                System.out.println("Problem operating on filesystem");
            }
            play.Logger.debug("File uploaded");
            ObjectNode result = Json.newObject();
            result.put("src", "/assets/images/upload/" + fileName);
            Logger.debug("<img src=" + result.get("src") + ">");
            return ok("<img src=" + result.get("src") + ">").as("text/html");
        } else {
            play.Logger.debug("File not uploaded");

            flash("error", "Missing file");
            return badRequest("Fehler");
        }
    }


    public static Result createSupplier() {
        Form<Supplier> supplierForm = form(Supplier.class).bindFromRequest();
        if (supplierForm.hasErrors()) {
            return badRequest(createSupplierPage.render(supplierForm));
        } else {
            Supplier supplier = supplierForm.get();
            supplier.save();
            flash("success", Messages.get("supplier.create"));
            return redirect(routes.Administration.adminPage());
        }
    }
}
