package models;

import com.avaje.ebean.Page;
import play.Logger;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.i18n.Messages;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@SequenceGenerator(name = "category_seq", sequenceName = "category_seq")
public class Category extends Model implements Comparable<Category> {

    private static final long serialVersionUID = 1L;

    public Category() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    public Long id;

    @Required(message = "name.required")
    public String name;

    public String description;

    //@NotNull
    public String imagePath;

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


    /**
     * Return a page of Category
     *
     * @param page     Page to display
     * @param pageSize Number of Category per page
     * @param sortBy   Category property used for sorting
     * @param order    Sort order (either or asc or desc)
     * @param filter   Filter applied on the name column
     */
    public static Page<Category> page(int page, int pageSize, String sortBy, String order, String filter) {
        return
                find.where()
                        .ilike("name", "%" + filter + "%")
                        .orderBy(sortBy + " " + order)
                        .fetch("products")
                        .findPagingList(pageSize)
                        .setFetchAhead(false)
                        .getPage(page);
    }

    public static List<Category> findSuperParentCategories() {
        return find.where().eq("parentCategory", null).orderBy().asc("id").findList();
    }

    public Category getSuperParentCategory() {
        char charIndex = '_';
        int index = this.name.indexOf(charIndex);
        String name = index == -1 ? this.name : this.name.substring(0, index);
        return this.findByName(name);
    }

    public List<Category> getParentCategories() {
        String[] items = this.name.split("_");
        List<Category> categories = new ArrayList<>();
        String name = "";
        for (String item : items) {
            name += item;
            Category cat = findByName(name);
            if (cat != null) {
                categories.add(cat);
            }
            name += "_";
        }
        return categories;
    }

    public String getRoutePath() {
        return this.name.replace('_', '/');
    }

    public static Category findByName(String name) {
        return name == null ? null : find.where().eq("name", name).findUnique();
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
