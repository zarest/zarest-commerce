import com.avaje.ebean.Ebean;
import models.Category;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

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
}
