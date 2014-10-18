package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class Category extends Model implements Comparable<Category> {

    @Id
    public Long id;
    public String name;
    @ManyToOne
    public Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    public Set<Category> subCategories = new TreeSet<Category>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    public List<Product> products = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public static Finder<Long, Category> find = new Finder<Long, Category>(
            Long.class, Category.class);

    public static List<Category> findSuperParentCategories() {
        return find.where().eq("parentCategory", null).findList();
    }

    @Override
    public int compareTo(Category o) {
        return this.name.compareTo(o.name);
    }
}
