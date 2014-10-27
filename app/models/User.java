package models;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;
import play.i18n.Messages;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meysamabl on 10/25/14.
 */
@Entity
public class User extends Model {

    @Id
    @Email
    @Required(message = "email.required")
    public String email;
    @Required(message = "password.required")
    @Pattern(value = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", message = "password.validation")
    public String password;
    public String name;
    @Valid
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    public List<Address> addressList = new ArrayList<>();
    public String phone;
    public boolean isAdmin;

    public User() {

    }



    public User(String email, String name, String password) {
        this.email = email;
        this.name= name;
        this.password = password;
    }

    // -- Queries

    public static Model.Finder<String, User> find = new Model.Finder<String, User>(String.class, User.class);

    /**
     * Retrieve all users.
     */
    public static List<User> findAll() {
        return find.all();
    }

    /**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    /**
     * Authenticate a User.
     */
    public static User authenticate(String email, String password) {
        return find.where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();
    }

    public static boolean adminAuthenticate(String email, String password) {
        User user = authenticate(email, password);
        return user != null && user.isAdmin ? true : false;
    }

    // --

    public String toString() {
        return "User(" + email + ")";
    }


}
