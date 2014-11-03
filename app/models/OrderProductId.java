package models;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by meysamabl on 11/2/14.
 */
@Embeddable
public class OrderProductId implements Serializable {

//    @ManyToOne
//    @JoinColumn(name = "order_id")
    private Long order;
//    @ManyToOne
//    @JoinColumn(name = "product_id")
    private Long product;

    public Order getOrder() {
        return Order.find.byId(order);
    }

    public void setOrder(Order order) {
        this.order = order.id;
    }

    public Product getProduct() {
        return Product.find.byId(product);
    }

    public void setProduct(Product product) {
        this.product = product.id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderProductId that = (OrderProductId) o;

        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (product != null ? !product.equals(that.product) : that.product != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (order != null ? order.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }

}
