package com.bestbuy.crudtest;

import com.bestbuy.playgroundinfo.ProductSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)

public class ProductsCrudTest extends TestBase {

    static String name = "Duracell - AAA Batteries (4-Pack)" + TestUtils.getRandomValue();
    static String type = "Hardgood";
    static int price = 6;
    static Integer shipping = 12;
    static String upc = "041333424019";
    static String description = "Long lasting energy";
    static String manufacturer = "DanaBattery";
    static String model = "MAKE2022";
    static String url = "danabatteries.com";
    static String image = "http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg";
    static int productID;
    @Steps
    ProductSteps productSteps;

    @Title("This will create new product")
    @Test
    public void test001() {
        ValidatableResponse response = productSteps.createProduct(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        response.log().all().statusCode(201);
        productID = response.log().all().extract().path("id");

    }

    @Title("Verify Product was added")
    @Test
    public void test002() {
        HashMap<String, Object> productMap = productSteps.getProductInfoById(productID);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("Verify Product was Updated")
    @Test
    public void test003() {
        name = name + "_updated";
        productSteps.updateProduct(productID, name).statusCode(200).log().all();
        HashMap<String, Object> productMapData = productSteps.getProductInfoById(productID);
        Assert.assertThat(productMapData, hasValue(name));
    }

    @Title("Verify product was deleted")
    @Test
    public void test004() {
        productSteps.deleteProduct(productID).statusCode(200).log().all();

    }
}
