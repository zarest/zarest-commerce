package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.i18n.Messages;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "_type", discriminatorType = DiscriminatorType.INTEGER)
public abstract class GenericUser extends Model {

    @Id
    @Constraints.Email
    @Constraints.Required(message = "email.required")
    public String email;
    @Constraints.Required(message = "password.required")
    @Constraints.Pattern(value = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9])(?=\\S+$).{8,}$", message = "password.validation")
    public String password;
    @Transient
    @Constraints.Required(message = "password.required")
    public String confirmedPassword;
    @Constraints.Required(message = "name.required")
    public String name;

    public GenericUser() {

    }

    public GenericUser(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String validate() {
        if (!password.equals(confirmedPassword)) {
            return Messages.get("password.notMatched");
        }
        return null;
    }

}
