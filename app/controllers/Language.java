package controllers;

import play.i18n.Lang;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Created by meysamabl on 10/21/14.
 */
public class Language extends Action.Simple {
    @Override
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {

        ctx.args.put("currLang", ctx.lang());
        return delegate.call(ctx);
    }

    public static Lang CurrentLang() {
        return (Lang) Http.Context.current().args.get("currLang");
    }
}
