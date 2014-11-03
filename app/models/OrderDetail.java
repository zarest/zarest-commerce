package models;

import org.joda.time.DateTime;
import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by meysamabl on 11/2/14.
 */
@Entity
@Table(name = "order_details", uniqueConstraints=@UniqueConstraint(columnNames={"order_id", "product_id"}))
//@AssociationOverrides({
//        @AssociationOverride(name = "pk.order",
//                joinColumns = @JoinColumn(name = "order_id")),
//        @AssociationOverride(name = "pk.product",
//                joinColumns = @JoinColumn(name = "product_id"))})
public class OrderDetail extends Model {

//    @EmbeddedId
//    public OrderProductId pk = new OrderProductId();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @ManyToOne
    public Order order;
    @ManyToOne
    public Product product;

    public BigDecimal price;
    public Short quantity;
    public Double discount;
    // This is typically a calculated field based on Price * Quantity * Discount
    public BigDecimal total;
    public String size;
    public String color;
    public boolean fulfilled;
    @Formats.DateTime(pattern = "dd-mm-yyyy")
    public DateTime billDate;
    @Formats.DateTime(pattern = "dd-mm-yyyy")
    public DateTime shipDate;
    /*
    This is the Foreign Key to the Shippers Table that says what shipping company is used.
    If you use more than one company and do not ship complete then you may want to have a ShipperID
    in the OrderDetails table so you can reference that one item went on Date X Federal Express and the other
    items went on Date Y UPS Ground.
     */
    public Long shipperId;
    public BigDecimal frieght;
    public BigDecimal salesTax;

//    @Transient
//    public Order getOrder() {
//        return pk.getOrder();
//    }
//
//    public void setOrder(Order order) {
//        this.pk.setOrder(order);
//    }
//
//    @Transient
//    public Product getProduct() {
//        return pk.getProduct();
//    }
//
//    public void setProduct(Product product) {
//        this.pk.setProduct(product);
//    }

    public static Finder<Long, OrderDetail> find = new Finder<Long, OrderDetail>(Long.class, OrderDetail.class);
}
