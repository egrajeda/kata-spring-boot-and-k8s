package com.egrajeda.todoedgeservice;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@EnableFeignClients
@SpringBootApplication
public class TodoEdgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoEdgeServiceApplication.class, args);
    }
}

@Data
class Todo {

    private String title;
}

@FeignClient(name = "todo-service")
interface TodoClient {

    @GetMapping("/todos")
    Resources<Todo> readTodos();
}

@RestController
class TodoApiAdapterRestController {

    private final TodoClient todoClient;

    TodoApiAdapterRestController(TodoClient todoClient) {
        this.todoClient = todoClient;
    }

    @GetMapping("/todos")
    public Collection<Todo> todos() {
        return todoClient.readTodos()
                .getContent();
    }
}
