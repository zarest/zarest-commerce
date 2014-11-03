package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by meysamabl on 11/2/14.
 */
@Entity
public class Shipper extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String companyName;
    @Constraints.Pattern(value = "^\\+(?:[0-9] ?){6,14}[0-9]$",
            message = "phone.validation")
    public String phone;



}
