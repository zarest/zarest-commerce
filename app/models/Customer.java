package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by meysamabl on 10/18/14.
 */
@Entity
public class Customer extends Model {

    @Id
    public Long id;

}
