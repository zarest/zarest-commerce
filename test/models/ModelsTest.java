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
    //@Ignore
    public void createAndRetrieveUser() {
        User user = new User("bob@gmail.com", "Bob", "secret");
        user.save();
        User bob = User.find.where().eq("email", "bob@gmail.com").findUnique();
        assertNotNull(bob);
        //assertEquals("03", bob.addressList.get(0).addressType);
        assertEquals("Bob", bob.name);
    }

    @Test
    public void createAndRetrieveCustomer() {
        Customer customer = new Customer("meysam@gmail.com", "meysam", "secret");
        Address address = new Address();
        address.country = Country.getListOfCountries(Locale.CANADA).get(0);
        customer.addressList.add(address);
        CreditCard creditCard = new CreditCard();
        creditCard.creditCardNumber = "49158006189134";
        customer.creditCards.add(creditCard);
        customer.save();
        Customer meysam = Customer.find.where().eq("email", "meysam@gmail.com").findUnique();
        assertNotNull(meysam);
        assertNotNull(meysam.creditCards.get(0));
        assertEquals("03", meysam.addressList.get(0).addressType);
        assertEquals("meysam", meysam.name);
    }
}