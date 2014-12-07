package controllers;

import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import jsmessages.JsMessages;
import models.Category;
import models.Product;
import models.User;
import play.*;
import play.api.mvc.*;
import play.cache.Cache;
import play.cache.Cached;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Lang;
import play.i18n.Messages;
import play.mvc.*;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static play.data.Form.form;

@With(CategoryMenu.class)
public class Application extends Controller {

    final static JsMessages messages = JsMessages.create(Play.application());

    public static Result jsMessages() {
        return ok(messages.generate("window.Messages"));
    }

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
            Logger.info("My URL: " + refererURL);
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
        return ok(product.render("products",
                Category.findSuperParentCategories()));
    }

    public static Result categoryProduct(
            String name, Boolean list, int page, int pageSize, String sortBy, String order, String filter) {
        Logger.debug("Path: {}", name);
        Logger.debug("list: {}", list);
        Logger.debug("inside categoryProduct method");
        List<String> paths = Arrays.asList(name.split("/"));
        if (isNumeric(paths.get(paths.size() - 1))) {
            Long id = Long.parseLong(paths.get(paths.size() - 1));
            Product product = Product.find.byId(1l);
            return ok(viewProduct.render(product.productName, product));
        }
        String catName = name.replace('/', '_');
        Category cat = Category.findByName(catName);
        if (cat == null) {
            return notFound("PageNotFound");
        }
        if (!cat.products.isEmpty()) {
            return ok(showProducts.render(cat.name,
                    Product.pageForCategory(cat, page, pageSize, sortBy, order, filter), pageSize,
                    sortBy, order, filter, list));
        } else {
            return ok(product.render(cat.name, new ArrayList<>(cat.getSubCategories())));

        }
    }

    // in cache during 1800 seconds (30 min)
    @Cached(key = "pagingList", duration = 1800)
    public static Result pagingListProduct(
            Category category, Boolean list, int page, int pageSize, String sortBy, String order, String filter) {
        String uuid = session("uuid");
        if (uuid == null) {
            uuid = java.util.UUID.randomUUID().toString();
            session("uuid", uuid);
        }

        PagingList<Product> pagingList = null;
        pagingList = (PagingList<Product>) Cache.get(uuid + "pagingList");
        Logger.debug("list: {}", list);
        if (pagingList == null) {
            // 15 records in each page
            pagingList = Product.find.where()
                    .eq("category", category)
                    .ilike("productName", "%" + filter + "%")
                    .orderBy(sortBy + " " + order)
//                        .fetch("products")
                    .findPagingList(pageSize);
        }

        Page<Product> currentPage = pagingList.getPage(page);
        // pagineList save in cache with unique uuid from session
        Cache.set(uuid + "pagingList", pagingList);

        List<Product> products = currentPage.getList();
        Integer totalPageCount = pagingList.getTotalPageCount();

//        return ok(index.render(products, page, totalPageCount));
        return ok(showProducts.render(category.name,
                currentPage, pageSize,
                sortBy, order, filter, list));
    }


    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("jsRoutes",
                        routes.javascript.Administration.getSubCategories()
//                        controllers.routes.javascript.Projects.add(),
//                        controllers.routes.javascript.Projects.delete(),
//                        controllers.routes.javascript.Projects.rename(),
//                        controllers.routes.javascript.Projects.addGroup()
                )
        );
    }

    public static Result shoppingCart() {
        return ok(shoppingCart.render(Messages.get("shoppingCart"), Product.find.all()));
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
        Logger.info("We are here");
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
