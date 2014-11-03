package controllers;

import play.Logger;
import play.i18n.Messages;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;
import scala.Option;

import java.util.*;

import static play.mvc.Http.Context.*;

public class Secured extends Security.Authenticator {

    public static final List<Country> COUNTRY_LIST;

    static {
        COUNTRY_LIST = Country.getListOfCountries(current().lang().toLocale());
    }

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.login());
    }

    public static Option<User> getCurrentUser() {
        return Option.apply(User.findByEmail(current().request().username()));
    }

    public static Map<String, String> getListOfCountries() {
        Map<String, String> countries = new TreeMap<>();
        COUNTRY_LIST.forEach((country) -> {
            countries.put(country.getCountryCode(), country.getCountryName());
        });
        return countries;
    }

    public static Map<String, String> getCreditCardTypes() {
        Map<String, String> creditCardTypes = new LinkedHashMap<>();
        CreditCard.getCreditCardTypes().stream().forEach(credit -> creditCardTypes.put(credit.name(), credit.name()));
        return creditCardTypes;
    }

    public static Country findByCountryCode(String code) {
        return COUNTRY_LIST.stream().filter(c -> c.getCountryCode().equals(code)).findFirst().get();
    }

    public static Map<String, String> getSuperParentCategories() {
        Map<String, String> categories = new LinkedHashMap<>();
        Category.findSuperParentCategories().forEach(category -> categories.put(category.id.toString(), Messages.get(category.name)));
        return categories;
    }

    public static Map<String, String> getSubCategories(String id) {
        Map<String, String> categories = new LinkedHashMap<>();
        if (id != null && !id.isEmpty()) {
            Category cat = Category.find.byId(Long.parseLong(id));
            cat.subCategories.forEach(category -> categories.put(category.id.toString(), Messages.get(category.name)));
            return categories;
        }
        return categories;
    }


    // Access rights

}