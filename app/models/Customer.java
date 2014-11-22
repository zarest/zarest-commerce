package models;

import play.data.validation.Constraints.*;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meysamabl on 10/18/14.
 */
@Entity
@DiscriminatorValue("1")
public class Customer extends GenericUser {

    @Valid
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="customer_address")
    public List<Address> addressList = new ArrayList<>();
    @Required(message = "phone.required")
    @Pattern(value = "^\\+(?:[0-9] ?){6,14}[0-9]$",
            message = "phone.validation")
    public String phone;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "customer")
    public List<CreditCard> creditCards = new ArrayList<>();

    public boolean isShippingAddress;

    public Customer() {
        super();
    }

    public Customer(String email, String name, String password) {
        super(email, name, password);
    }

    public static Finder<String, Customer> find = new Finder<String, Customer>(String.class, Customer.class);

}
