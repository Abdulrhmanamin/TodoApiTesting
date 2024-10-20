package com.qacart.todo.apis;

import com.qacart.todo.base.Specs;
import com.qacart.todo.data.Routes;
import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserAPIS {

    public static Response register(User user){
        return
                given()
                        .spec(Specs.getRequestSpecs())
                        .body(user)
                        .when().post(Routes.REGISTER_ROUTE)
                        .then()
                        .log().all()
                        .extract().response();
    }

    public static Response login(User user){
        return
                given()
                        .spec(Specs.getRequestSpecs())
                        .body(user)
                        .when()
                        .post(Routes.LOGIN_ROUTE)
                        .then()
                        .log().all()
                        .extract().response();
    }
}
