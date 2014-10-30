package models;

import play.libs.Scala;
import scala.Option;

import javax.persistence.Embeddable;
import java.util.*;
import java.util.stream.Collectors;

import static play.mvc.Http.Context.current;

/**
 * Created by meysamabl on 10/25/14.
 */
@Embeddable
public class Country implements Comparable<Country> {

    private String countryCode;
    private String countryName;

    public Country(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getCountryName() {
        return this.countryName;
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

    public static List<Country> getListOfCountries() {

        String[] locales = Locale.getISOCountries();
        List<Country> countries = new ArrayList<>();

        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countries.add(new Country(obj.getCountry(), obj.getDisplayName()));
            //obj.getDisplayCountry(locale)
        }
        return countries;

    }

    @Override
    public int compareTo(Country o) {
        return this.countryName.compareTo(o.countryName);
    }
}
