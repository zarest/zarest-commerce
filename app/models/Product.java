package models;

import play.data.validation.Constraints.MaxLength;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by meysamabl on 10/18/14.
 */
@Entity
public class Product extends Model {

    @Id
    private Long id;
    @MaxLength(50)
    private String sku;
    @MaxLength(50)
    private String supplierProdId;
    @MaxLength(60)
    private String productName;
    @MaxLength(255)
    private String productDescription;
    //@OneToMany
    private Long supplier;
    @ManyToOne
    private Category category;
    private Long quantityPerUnit;

}
