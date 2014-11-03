package models;

import models.*;
import org.junit.*;

import static org.junit.Assert.*;

import play.Logger;
import play.core.Router;
import play.test.WithApplication;

import java.math.BigDecimal;
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

    @Test
    public void createProduct() {
        Product p = new Product();
        Category cat = Category.find.byId(1l);
        p.productName = "Test Product";
        p.category = cat;
        Image image = new Image();
        image.caption = "This is Caption";
        image.filePath = "/public/images/favicon.png";
        p.pictures.add(image);
        Supplier supplier = new Supplier();
        supplier.companyName = "TestSupplier";
        supplier.save();
        Supplier sup = Supplier.find.byId(1l);
        p.supplier = sup;
        p.unitPrice = BigDecimal.valueOf(200l);
        p.save();
        Product prod = Product.find.byId(1l);
        assertNotNull(prod);
        assertEquals(prod.productName, "Test Product");
        assertEquals(cat.name, prod.category.name);
    }


}