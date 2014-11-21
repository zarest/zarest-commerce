package models;

import play.Logger;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by meysamabl on 11/1/14.
 */
@Entity
public class Supplier extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Constraints.Required(message = "companyName.required")
    public String companyName;
    public String contactFirstName;
    public String contactLastName;
    public String contactTitle;
    @Valid
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Supplier_address")
    public List<Address> addressList = new ArrayList<>();
    @Constraints.Required(message = "phone.required")
    @Constraints.Pattern(value = "^\\+(?:[0-9] ?){6,14}[0-9]$",
            message = "phone.validation")
    public String phone;
    @Constraints.Pattern(value = "^\\+(?:[0-9] ?){6,14}[0-9]$",
            message = "fax.validation")
    public String fax;
    @Constraints.Required(message = "email.required")
    @Constraints.Email(message = "email.validation")
    public String email;
    @Constraints.Pattern(value = "^[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|COM|ORG|NET|MIL|EDU)",
            message = "website.validation")
    public String website;
    /*
    Description of how you pay the Supplier
    (check, Purchase order, credit card, Net 30, etc.).
    This can be held as text or connected to a
    separate PaymentTypes Table using a PaymentID in
    both the Suppliers Table and the PaymentTypes Table.
     */
    public String PaymentMethods;
    //Description of Types of Discounts available from the Supplier
    public String discountTypes;
    public Float discountRate;
    /*
    Description of types of goods available from the Supplier.
    This can be held as text or connected to a
    separate GoodsCategory Table using a CategoryID in both the
    Suppliers Table and the GoodsCategory Table.
     */
    public String typeGoods;
    public boolean discountAvailable;
    /*
    Reorder Level - When to Reorder products. Drumbeat E-commerce used UnitsInStock - UnitsonOrder = X
    If X is > ReorderLevel then "Item is in Stock"
    If X is <= ReorderLevel then "Item is Out of Stock" This is helpful to display to customers and useful for inventory purposes
     */
    public boolean currentOrder;

    //Your customer ID with the Supplier.
    public String customerId;
    /*
    A URL to the Supplier Web Page with sizing info on their products
    (helpful for your customers)

     */
    @Constraints.Pattern(value = "^[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|COM|ORG|NET|MIL|EDU)",
            message = "sizeURL.validation")
    public String sizeURL;
    /*
    A URL to the Supplier Web Page with color info on their
    products (helpful for your customers)
     */
    @Constraints.Pattern(value = "^[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|COM|ORG|NET|MIL|EDU)",
            message = "colorURL.validation")
    public String colorURL;
    /*
    Link to an Image file of the Supplier's Logo or a URL reference to the image
     */
    public String logoImage;
    /*
    a product ranking used for displaying Supplier specials or showing certain
    items as higher in a sort. Like the individual item ranking,
    here you can set one Supplier to show higher on a list than another
    (regardless of alphabet)
     */
    public Integer ranking;
    public String note;

    public static Finder<Long, Supplier> find = new Finder<Long, Supplier>(Long.class, Supplier.class);

    public static Map<String, String> options() {
        Map<String, String> options = new LinkedHashMap<>();
        for (Supplier sup : find.all()) {
            options.put(sup.id.toString(), sup.companyName);
        }
        return options;
    }
}
