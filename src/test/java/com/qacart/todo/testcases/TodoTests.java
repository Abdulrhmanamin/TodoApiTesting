package com.qacart.todo.testcases;

import com.qacart.todo.apis.TodoAPIS;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.Todo;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@Feature("Todo Feature")
public class TodoTests {


    @Story("Adding Todo")
    @Test(description = "Should Be Able To Add Todo")
    public void shouldBeAbleToAddTodo(){

        Todo todo = TodoSteps.generateTodoData();

        String token = UserSteps.getUserToken();

        Response response = TodoAPIS.addTodo(todo,token);
        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnedTodo.getIsCompleted(),equalTo(todo.getIsCompleted()));
        assertThat(returnedTodo.getItem(),equalTo(todo.getItem()));

    }

    @Story("Adding Todo")
    @Test(description = "Should Be Not Able To Add Todo With Missing IsCompleted")
    public void shouldBeNotAbleToAddTodoWithMissingIsCompleted(){
        Todo todo = new Todo("learn appium");

        String token = UserSteps.getUserToken();

        Response response = TodoAPIS.addTodo(todo,token);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(),equalTo(400));
        assertThat(returnedError.getMessage(),equalTo(ErrorMessages.IS_COMPLETED_REQUIRED));

    }
    @Story("show todo")
    @Test(description = "Should Be Able To Get Todo By Id")
    public void shouldBeAbleToGetTodoById(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoData();
        String todoId =TodoSteps.getTodoId(todo,token);


        Response response = TodoAPIS.getTodo(token,todoId);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getIsCompleted(),equalTo(false));
        assertThat(returnedTodo.getItem(),equalTo(todo.getItem()));
    }

    @Story("deleting todo")
    @Test(description = "Should Be Able To Delete Todo By Id")
    public void shouldBeAbleToDeleteTodoById(){

        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoData();
        String todoId =TodoSteps.getTodoId(todo,token);

        Response response =TodoAPIS.deleteTodo(token,todoId);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getIsCompleted(),equalTo(false));
        assertThat(returnedTodo.getItem(),equalTo(todo.getItem()));
    }
}
