package models;

import org.hibernate.validator.constraints.CreditCardNumber;
import play.data.validation.Constraints.*;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.*;

/**
 * Created by meysamabl on 10/27/14.
 */
@Entity
public class CreditCard extends Model {

    @Id
    @GeneratedValue
    public Long id;
    @Required(message = "creditCardNumber.required")
    @Pattern(value = "^(?:4[0-9]{12}(?:[0-9]{3})?" +        // Visa
            "|5[1-5][0-9]{14}" +                            // MasterCard
            "|3[47][0-9]{13}" +                             // American Express
            "|3(?:0[0-5]|[68][0-9])[0-9]{11}" +             // Diners Club
            "|6(?:011|5[0-9]{2})[0-9]{12}" +                // Discover
            "|(?:2131|1800|35\\d{3})\\d{11}" +              //JCB
            ")$", message = "creditCardNumber.validation")
    public String creditCardNumber;
    @Required(message = "expDate.required")
    public Date expDate;
    public String creditCardType;
    @ManyToOne
    public Customer customer;

    public enum CreditCardType {
        MASTERCARD, VISA, AMERICANEXPRESS;

    }


    public static Map<String, String> creditCardTypeOptions() {
        Map<String, String> creditCardTypes = new LinkedHashMap<>();
        for (CreditCardType type : CreditCardType.values()) {
            creditCardTypes.put(type.name(), type.name());
        }
        //CreditCard.getCreditCardTypes().stream().forEach(credit -> creditCardTypes.put(credit.name(), credit.name()));
        return creditCardTypes;
    }
}
