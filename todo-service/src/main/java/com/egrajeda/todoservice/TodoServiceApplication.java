package com.egrajeda.todoservice;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;

@SpringBootApplication
public class TodoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoServiceApplication.class, args);
    }
}

@Data
@NoArgsConstructor
@Entity
class Todo {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    public Todo(String title) {
        this.title = title;
    }
}

@RepositoryRestResource
interface TodoRepository extends JpaRepository<Todo, Long> {
}

@Component
class TodoInitializer implements CommandLineRunner {

    private final TodoRepository todoRepository;

    TodoInitializer(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("Launch new version", "Finish Kubernetes course", "Wash clothes")
                .forEach(todo -> todoRepository.save(new Todo(todo)));

        todoRepository.findAll().forEach(System.out::println);
    }
}
