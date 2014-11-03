package models;

import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by meysamabl on 11/2/14.
 */
@Entity
public class Payment extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String paymentType;
    public boolean allowed;


    public static Finder<Long, Payment> find = new Finder<Long, Payment>(Long.class, Payment.class);
}
