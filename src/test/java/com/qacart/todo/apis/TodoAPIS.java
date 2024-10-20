package com.qacart.todo.apis;

import com.qacart.todo.base.Specs;
import com.qacart.todo.data.Routes;
import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoAPIS {
    public static Response addTodo(Todo todo,String token){
        return given()
                .spec(Specs.getRequestSpecs())
                .body(todo)
                .auth().oauth2(token)
                .when().post(Routes.TODO_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response getTodo (String token,String todoId){
        return given()
                .spec(Specs.getRequestSpecs())
                .auth().oauth2(token)
                .when().get(Routes.TODO_ROUTE+"/" +todoId)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteTodo (String token,String todoId){
        return given()
                .spec(Specs.getRequestSpecs())
                .auth().oauth2(token)
                .when().delete(Routes.TODO_ROUTE+"/" +todoId)
                .then()
                .log().all()
                .extract().response();
    }
}
