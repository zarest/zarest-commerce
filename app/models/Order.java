package models;

import org.joda.time.DateTime;
import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by meysamabl on 11/2/14.
 */
@Entity
@Table(name = "oder_table")
public class Order extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "order_id", unique = true, nullable = false)
    public Long id;
    @Valid
    @ManyToOne
    public Customer customer;
    @Valid
    @ManyToOne
    public Payment payment;
    @Formats.DateTime(pattern = "dd-mm-yyyy")
    public DateTime orderDate;
    @Formats.DateTime(pattern = "dd-mm-yyyy")
    public DateTime requiredDate;
    @Formats.DateTime(pattern = "dd-mm-yyyy")
    public DateTime shipDate;
    @Valid
    @ManyToOne
    public Shipper shipper;
    /*
    Freight Charges. Again it is in this table only if things are shipped complete.
    If not you would need to track individual shipping charges in the OrderDetails Table.
     */
    public BigDecimal freight;
    //Sales Tax on the entire order
    public BigDecimal salesTax;
    //A time stamp
    public String timeStamp;
    //Used by CyberCash for credit card transaction approval
    public String transactStatus;
    //Used by CyberCash for credit card transaction approval
    public String errLoc;
    public String errMsg;
    public boolean fulfilled;
    public boolean deleted;
    public BigDecimal paid;
    @Formats.DateTime(pattern = "dd-mm-yyyy")
    public DateTime paymentDate;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    public Set<OrderDetail> orderDetails = new HashSet<>();

    public static Finder<Long, Order> find = new Finder<Long, Order>(Long.class, Order.class);
}



