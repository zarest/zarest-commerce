package controllers;

import models.Category;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;

/**
 * Created by meysamabl on 10/22/14.
 */
public class CategoryMenu extends Action.Simple {

    @Override
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        ctx.args.put("categories", Category.find.orderBy().asc("id").findList());
        return delegate.call(ctx);
    }

    @SuppressWarnings("unchecked")
    public static List<Category> getCategories() {
        return (List<Category>) Http.Context.current().args.get("categories");
    }
}
