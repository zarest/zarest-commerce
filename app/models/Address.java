package models;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

import javax.persistence.*;
import javax.validation.Valid;

/**
 * Created by meysamabl on 10/25/14.
 */
@Entity
public class Address extends Model {
    @Id
    @GeneratedValue
    public Long id;
    @Required(message = "address1.required")
    public String address1;
    public String address2;
    @Required(message = "city.required")
    public String city;
    @Required(message = "state.required")
    public String state;
    @Required(message = "country.required")
    @Embedded
    public Country country;
    @Required(message = "postalCode.required")
    public String postalCode;
    @Required
    public String addressType = AddressType.SHIPPING.code;
    @Valid
    @OneToOne
    public Customer customer;

    public enum AddressType {
        NORMAL("01"), BILLING("02"), SHIPPING("03");

        String code;

        AddressType(String code) {
            this.code = code;
        }
    }

    public static Finder<Long, Address> find = new Finder<Long, Address>(Long.class, Address.class);


}