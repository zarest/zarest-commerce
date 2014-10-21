import com.avaje.ebean.Ebean;
import models.Category;
import play.Application;
import play.GlobalSettings;
import play.api.mvc.Handler;
import play.libs.F;
import play.libs.Yaml;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by meysamabl on 10/18/14.
 */
public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        if(Category.find.findRowCount() == 0) {

            @SuppressWarnings("unchecked")
            Map<String,List<Object>> all = (Map<String,List<Object>>) Yaml.load("initial-data.yml");

            // Insert users first
            Ebean.save(all.get("categories"));

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

        if (request.host().equals("fa.meysamabl.com")
                && (request.cookie("PLAY_LANG") == null || !request.cookie("PLAY_LANG").value().equals("fa"))) {
            return new Action.Simple() {
                @Override
                public F.Promise<Result> call(Http.Context ctx) throws Throwable {
                    ctx.changeLang("fa");
                    return delegate.call(ctx);
                }
            };

        } else {
            return super.onRequest(request, actionMethod);
        }
    }
}
