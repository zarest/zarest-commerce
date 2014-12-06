package models;

import com.avaje.ebean.Page;
import play.data.validation.Constraints.*;
import play.db.ebean.Model;
import play.i18n.Messages;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.*;

/**
 * Created by meysamabl on 10/18/14.
 */
@Entity
public class Product extends Model implements Comparable<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "product_id", unique = true, nullable = false)
    public Long id;
    //SKU number from you or the Supplier
    @Column(name = "sku", unique = true, nullable = false)
    @MaxLength(50)
    @Required
    public String sku;
    //The Vendor's Product ID (could be SKU or their own system). (Could be a duplication of SKU above.)
    @MaxLength(50)
    public String supplierProdId;
    @Required
    @MaxLength(60)
    public String productName;
    @MaxLength(255)
    public String productDescription;
    public String productDetails;
    @Required
    @ManyToOne
    public Supplier supplier;
    @Required
    @ManyToOne
    public Category category;
    //Quantity that items are shipped per unit from supplier.
    // E.g. 6/case. Mostly for inventory and ordering purposes. Can be used in arithmetic expressions.
    public Integer quantityPerUnit;
    //Unit Size - goes with QuantityPerUnit. This is case, each, dozen, etc.
    public String unitSize;
    @Required
    public BigDecimal unitPrice;

    //Manufacturer's Suggested Retail Price.
    // This may be different than the Unit Price and
    // helps when you are showing discounts off MSRP.
    public BigDecimal msrp;
    //can be in different table using sizeId
    @Required
    public String availableSizes;
    //can be in different table using colorId
    @Required
    public String availableColors;
    public Double discount;
    //Item weight for shipping calculation
    public Double unitWeight;
    public Short unitInStock;
    public Short unitOnOrder;
    public Short reorderLevel;
    public boolean productAvailable;
    public boolean discountAvailable;
    public boolean currentOrder;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    public List<Image> images = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    public Set<OrderDetail> orderDetails = new HashSet<>();
    //a product ranking used for displaying item specials
    // or showing certain items as higher in a sort
    public Integer ranking;
    public String warrantySpecification;
    public String note;
    @Temporal(TemporalType.DATE)
    public Date date = new Date();

    //    public String validate() {
//        if (images.isEmpty()) {
//            return Messages.get("image.required");
//        }
//        return null;
//    }
    public String getRoutePath() {
        String path = category.name + "_" + this.id;
        return path.replace('_', '/');
    }

    public static Finder<Long, Product> find = new Finder<Long, Product>(Long.class, Product.class);

    /**
     * Return a page of product for each category
     *
     * @param category products of the category
     * @param page     Page to display
     * @param pageSize Number of Product per page
     * @param sortBy   Product property used for sorting
     * @param order    Sort order (either or asc or desc)
     * @param filter   Filter applied on the name column
     */
    public static Page<Product> pageForCategory(Category category, int page, int pageSize, String sortBy, String order, String filter) {
        return
                find.where()
                        .eq("category", category)
                        .ilike("productName", "%" + filter + "%")
                        .orderBy(sortBy + " " + order)
//                        .fetch("products")
                        .findPagingList(pageSize)
                        .setFetchAhead(false)
                        .getPage(page);
    }

    /**
     * Return a page of Category
     *
     * @param page     Page to display
     * @param pageSize Number of Product per page
     * @param sortBy   Product property used for sorting
     * @param order    Sort order (either or asc or desc)
     * @param filter   Filter applied on the name column
     */
    public static Page<Product> page(int page, int pageSize, String sortBy, String order, String filter) {
        return
                find.where()
                        .ilike("productName", "%" + filter + "%")
                        .orderBy(sortBy + " " + order)
//                        .fetch("products")
                        .findPagingList(pageSize)
                        .setFetchAhead(false)
                        .getPage(page);
    }


    @Override
    public int compareTo(Product o) {
        return Collator.getInstance(new Locale("ar")).compare(Messages.get(this.productName), Messages.get(o.productName));
    }

}
