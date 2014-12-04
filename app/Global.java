import com.avaje.ebean.Ebean;
import controllers.Secured;
import models.Category;
import models.Country;
import models.Supplier;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.api.mvc.Handler;
import play.data.format.Formatters;
import play.db.ebean.Transactional;
import play.libs.F;
import play.libs.Yaml;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static play.mvc.Results.notFound;

/**
 * Created by meysamabl on 10/18/14.
 */
public class Global extends GlobalSettings {

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

        Formatters.register(Category.class, new Formatters.SimpleFormatter<Category>() {

            @Override
            public Category parse(String input, Locale locale) throws ParseException {
                return Category.find.byId(Long.parseLong(input));
            }

            @Override
            public String print(Category category, Locale locale) {
                return category.id.toString();
            }
        });

        Formatters.register(Supplier.class, new Formatters.SimpleFormatter<Supplier>() {

            @Override
            public Supplier parse(String input, Locale locale) throws ParseException {
                return Supplier.find.byId(Long.parseLong(input));
            }

            @Override
            public String print(Supplier supplier, Locale locale) {
                return supplier.id.toString();
            }
        });
    }

    @Override
    @Transactional
    public void onStart(Application app) {
        if (Category.find.findRowCount() == 0) {

            @SuppressWarnings("unchecked")
            Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial-data.yml");

            // Insert users first
            Ebean.save(all.get("users"));
            // Add categories
            //Ebean.save(all.get("categories"));

            // Insert projects
//            Ebean.save(all.get("projects"));
//            for(Object project: all.get("projects")) {
//                // Insert the project/user relation
//                Ebean.saveManyToManyAssociations(project, "members");
//            }
//
//            // Insert tasks
//            Ebean.save(all.get("tasks"));

        }
    }

    //@Override
    public Action onRequest(final Http.Request request, final Method actionMethod) {
        Logger.info("Request: {}", request.path());
        if (request.host().equals("fa.meysamabl.com")
                && (request.cookie("PLAY_LANG") == null || !request.cookie("PLAY_LANG").value().equals("fa"))) {
            return new Action.Simple() {
                @Override
                public F.Promise<Result> call(Http.Context ctx) throws Throwable {
                    ctx.changeLang("fa");
                    return delegate.call(ctx);
                }
            };

        }
//        if (request.username() == null && !request.path().startsWith("/login")) {
//
//            return new Action.Simple() {
//                @Override
//                public F.Promise<Result> call(Http.Context ctx) throws Throwable {
//                    temporaryRedirect("/login");
//                    Logger.info("Inside the user request");
//                    return delegate.call(ctx);
//                }
//            };
//        }
        return super.onRequest(request, actionMethod);

    }

}
