package models;

import org.apache.commons.codec.binary.Base64;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class Category extends Model implements Comparable<Category> {

    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;

    @Required(message = "name.required")
    public String name;

    public String description;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 100000)
    public byte[] picture;

    public boolean active;

    @ManyToOne
    public Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    public Set<Category> subCategories = new TreeSet<Category>();

    @Valid
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    public List<Product> products = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public static Finder<Long, Category> find = new Finder<Long, Category>(
            Long.class, Category.class);

    public static List<Category> findSuperParentCategories() {
        return find.where().eq("parentCategory", null).orderBy().asc("id").findList();
    }

    @Override
    public int compareTo(Category o) {
        return this.name.compareTo(o.name);
    }
}
