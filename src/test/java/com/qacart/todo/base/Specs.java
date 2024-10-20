package com.qacart.todo.base;

import com.qacart.todo.data.Routes;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {

    public static RequestSpecification getRequestSpecs(){
        return  given()
                .baseUri(Routes.BASE_URL)
                .contentType(ContentType.JSON)
                .log().all();
    }
}
