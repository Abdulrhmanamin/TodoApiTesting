package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.UserAPIS;
import com.qacart.todo.models.User;
import io.restassured.response.Response;

public class UserSteps {

    public static User generateUserData(){
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String password = "12345678";

        return new User(firstName,lastName,email,password);
    }
    public static User getRegisteredUser(){
        User user = generateUserData();
        UserAPIS.register(user);
        return user;
    }

    public static String getUserToken(){
        User user = generateUserData();
        Response response =UserAPIS.register(user);
        return response.body().path("access_token");
    }
}
