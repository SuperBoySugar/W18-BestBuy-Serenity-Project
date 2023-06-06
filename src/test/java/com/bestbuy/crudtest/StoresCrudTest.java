package com.bestbuy.crudtest;

import com.bestbuy.playgroundinfo.StoresSteps;
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
public class StoresCrudTest extends TestBase {
    static  String name = "Mac" + TestUtils.getRandomValue();
    static String type = "SmallBox" + TestUtils.getRandomValue();
    static String address = "55 Downing Street";
    static String address2 = "";
    static String city = "London";
    static String state = "Sussex";
    static String zip = "W4 6FG";
    static int lat = 26;
    static int lng = 30;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";

    static int storeID;

    @Steps
    StoresSteps storesSteps;

    @Title("This will create a new store")
    @Test
    public void test001(){
        HashMap<Object, Object> serviceData = new HashMap<>();
        ValidatableResponse response = storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours, serviceData);
        response.log().all().statusCode(201);
        storeID = response.log().all().extract().path("id");
    }

    @Title("This will verify store was added")
    @Test
    public void test002(){
        HashMap<String, Object> storeMap = storesSteps.getStoreInfoByName(storeID);
        Assert.assertThat(storeMap, hasValue(name));
    }

    @Title("This will update the service information")
    @Test
    public void test003(){
        name = name + "_updated";
        storesSteps.updateStore(storeID, name);
        HashMap<String, ?> storeList = storesSteps.getStoreInfoByName(storeID);
        Assert.assertThat(storeList, hasValue(name));
        System.out.println(storeList);
    }

    @Title("This will delete the store by ID")
    @Test
    public void test004(){
        storesSteps.deleteStore(storeID).statusCode(201).log().all();
    }

}
