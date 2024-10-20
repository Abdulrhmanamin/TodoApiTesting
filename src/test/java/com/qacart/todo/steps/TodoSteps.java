package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.TodoAPIS;
import com.qacart.todo.models.Todo;
import io.restassured.response.Response;

public class TodoSteps {

    public static Todo generateTodoData(){
        Faker faker = new Faker();
        String item = faker.book().title();
        boolean isCompleted = false;
        return new Todo(isCompleted,item);
    }

    public static String getTodoId(Todo todo,String token){
        Response response = TodoAPIS.addTodo(todo,token);
        return response.body().path("_id");
    }
}
