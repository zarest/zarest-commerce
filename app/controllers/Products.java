package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.product;

/**
 * Created by meysamabl on 10/21/14.
 */
@With(CategoryMenu.class)
public class Products extends Controller  {

    public static Result productPage() {
        return ok(product.render());
    }
}
