package models;

import models.*;
import org.junit.*;

import static org.junit.Assert.*;

import play.Logger;
import play.test.WithApplication;

import java.util.List;
import java.util.Locale;

import static play.test.Helpers.*;

public class ModelsTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void testGetListOfCountries() {
        List<Country> countries = Country.getListOfCountries(Locale.CANADA);
        Logger.info(" " + countries.size());
        assertTrue(countries.size() == 250);
    }

    @Test
    @Ignore
    public void createAndRetrieveUser() {
        User user = new User("bob@gmail.com", "Bob", "secret");
        Address address = new Address();
        address.country = Country.getListOfCountries(Locale.CANADA).get(0);
        user.addressList.add(address);
        user.save();
        User bob = User.find.where().eq("email", "bob@gmail.com").findUnique();
        assertNotNull(bob);
        assertNotNull(bob.addressList.get(0));
        assertEquals("Bob", bob.name);
    }
}