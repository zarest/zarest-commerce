package models;

import play.data.validation.Constraints.MaxLength;
import play.db.ebean.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by meysamabl on 10/18/14.
 */
@Entity
public class Product extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "product_id", unique = true, nullable = false)
    public Long id;
    @Column(name = "sku", unique = true, nullable = false)
    @MaxLength(50)
    //SKU number from you or the Supplier
    public String sku;
    @MaxLength(50)
    //The Vendor's Product ID (could be SKU or their own system). (Could be a duplication of SKU above.)
    public String supplierProdId;
    @MaxLength(60)
    public String productName;
    @MaxLength(255)
    public String productDescription;
    @ManyToOne
    public Supplier supplier;
    @ManyToOne
    public Category category;
    //Quantity that items are shipped per unit from supplier.
    // E.g. 6/case. Mostly for inventory and ordering purposes. Can be used in arithmetic expressions.
    public Integer quantityPerUnit;
    //Unit Size - goes with QuantityPerUnit. This is case, each, dozen, etc.
    public String unitSize;
    public BigDecimal unitPrice;

    //Manufacturer's Suggested Retail Price.
    // This may be different than the Unit Price and
    // helps when you are showing discounts off MSRP.
    public BigDecimal msrp;
    //can be in different table using sizeId
    public String availableSizes;
    //can be in different table using colorId
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
    public List<Image> pictures = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    public Set<OrderDetail> orderDetails = new HashSet<>();
    //a product ranking used for displaying item specials
    // or showing certain items as higher in a sort
    public Integer ranking;
    public String note;


    public static Finder<Long, Product> find = new Finder<Long, Product>(Long.class, Product.class);


}
