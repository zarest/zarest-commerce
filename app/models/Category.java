package models;

import org.apache.commons.codec.binary.Base64;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.i18n.Messages;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.*;

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

    public static Map<String, String> parentCategoryOptions() {
        Map<String, String> categories = new LinkedHashMap<>();
        for (Category category : findSuperParentCategories()) {
            categories.put(category.id.toString(), Messages.get(category.name));
        }
        //Category.findSuperParentCategories().forEach(category -> categories.put(category.id.toString(), Messages.get(category.name)));
        return categories;
    }

    public static Map<String, String> subCategoryOptions(Long id) {
        Map<String, String> categories = new LinkedHashMap<>();
        Category cat = Category.find.byId(id);
        for (Category category : cat.subCategories) {
            categories.put(category.id.toString(), Messages.get(category.name));
        }
        //cat.subCategories.forEach(category -> categories.put(category.id.toString(), Messages.get(category.name)));
        return categories;
    }

    @Override
    public int compareTo(Category o) {
        return this.name.compareTo(o.name);
    }
}
