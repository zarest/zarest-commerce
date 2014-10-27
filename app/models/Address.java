package models;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by meysamabl on 10/25/14.
 */
@Entity
public class Address extends Model {
    @Id
    @GeneratedValue
    public Long id;
    @Required
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

    @ManyToOne
    public User user;

    public static Finder<Long, Address> find = new Finder<Long, Address>(Long.class, Address.class);


}