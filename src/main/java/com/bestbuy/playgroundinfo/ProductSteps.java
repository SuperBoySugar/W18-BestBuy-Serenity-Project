package com.bestbuy.playgroundinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductsPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductSteps {

    @Step
    public ValidatableResponse createProduct(String name, String type, int price, int shipping, String upc, String description, String manufacturer, String model, String url, String image) {

        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setPrice(price);
        productsPojo.setShipping(shipping);
        productsPojo.setUpc(upc);
        productsPojo.setDescription(description);
        productsPojo.setManufacturer(manufacturer);
        productsPojo.setModel(model);
        productsPojo.setUrl(url);
        productsPojo.setImage(image);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post(EndPoints.CREATE_PRODUCTS)
                .then();
    }

    @Step
    public HashMap<String, Object> getProductInfoById(int productID) {
        // String p1 = "findAll{it.productId == '";
        // String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .pathParam("productID", productID)
                .get(EndPoints.GET_SINGLE_PRODUCTS_BY_ID)
                .then()
                .statusCode(200)
                .extract().path("");

    }

    @Step("Update product details of id: {0}")
    public ValidatableResponse updateProduct(int productID, String name) {
        ProductsPojo productPojo = new ProductsPojo();
        productPojo.setName(name);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .body(productPojo)
                .pathParam("productID", productID)
                .when()
                .patch(EndPoints.UPDATE_PRODUCTS_BY_ID)
                .then();
    }


    @Step
    public ValidatableResponse deleteProduct(int productID) {

        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .when()
                .delete(EndPoints.DELETE_PRODUCTS_BY_ID)
                .then();
    }


}
