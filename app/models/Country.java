package models;

import javax.persistence.Embeddable;
import java.util.*;

/**
 * Created by meysamabl on 10/25/14.
 */
@Embeddable
public class Country {

    private String countryCode;
    private String countryName;

    public Country(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public static List<Country> getListOfCountries(Locale locale) {

        String[] locales = Locale.getISOCountries();
        List<Country> countries = new ArrayList<>();

        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countries.add(new Country(obj.getCountry(), obj.getDisplayName(locale)));
            //obj.getDisplayCountry(locale)
        }
        return countries;

    }
}
