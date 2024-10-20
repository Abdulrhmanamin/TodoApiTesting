package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserAPIS;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Feature("User Feature")
public class UserTests {

    @Story("Registration")
    @Test(description = "Should Be Able To Register")
    public void shouldBeAbleToRegister(){

        User user = UserSteps.generateUserData();

        Response response = UserAPIS.register(user);

        User returnedUser = response.body().as(User.class);

        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnedUser.getFirstName(),equalTo(user.getFirstName()));

    }
    @Story("Registration")
    @Test(description = "Should Be Not Able To Register With Same Email")
    public void shouldBeNotAbleToRegisterWithSameEmail(){
        User user =UserSteps.getRegisteredUser();

        Response response = UserAPIS.register(user);
        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(),equalTo(400));
        assertThat(returnedError.getMessage(),equalTo(ErrorMessages.EMAIL_IS_ALREADY_REGISTERED));
    }
    @Story("Registration")
    @Test(description = "Should Be Not Able To Register With Password Less Than 8 Characters Long")
    public void shouldBeNotAbleToRegisterWithPasswordLessThan8CharactersLong(){

        User user =UserSteps.generateUserData();
        user.setPassword("2256");


        Response response = UserAPIS.register(user);
        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(),equalTo(400));
        assertThat(returnedError.getMessage(),equalTo(ErrorMessages.WRONG_PASSWORD_LENGTH));

    }
    @Story("Login")
    @Test(description = "Should Be Able To Login With Valid Data")
    public void shouldBeAbleToLoginWithValidData(){
        User user =UserSteps.getRegisteredUser();
        User loginData = new User(user.getEmail(), user.getPassword());

        Response response = UserAPIS.login(loginData);


        User returnedUser = response.body().as(User.class);

        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedUser.getFirstName(),equalTo(user.getFirstName()));
        assertThat(returnedUser.getAccessToken(),not(equalTo(null)));

    }
    @Story("Login")
    @Test(description = "Should Be Not Able To Login With Wrong Password")
    public void shouldBeNotAbleToLoginWithWrongPassword(){

        User user =UserSteps.getRegisteredUser();
        User loginData = new User(user.getEmail(), "wrongPassword");

        Response response= UserAPIS.login(loginData);

        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(),equalTo(401));
        assertThat(returnedError.getMessage(),equalTo(ErrorMessages.EMAIL_OR_PASSWORD_IS_WRONG));


    }
}
