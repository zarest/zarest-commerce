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

    public static Country findByCountryCode(String code) {
        return COUNTRY_LIST.stream().filter(c -> c.getCountryCode().equals(code)).findFirst().get();
    }

}