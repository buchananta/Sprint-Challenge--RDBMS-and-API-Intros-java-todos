package com.lambdaschool.todos.controllers;

import com.lambdaschool.todos.models.Todos;
import com.lambdaschool.todos.models.User;
import com.lambdaschool.todos.repository.UserRepository;
import com.lambdaschool.todos.services.TodosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

/**
 * The entry point for client to access user, todos combinations
 */
@RestController
@RequestMapping("/todos")
public class TodosController<consumes>
{
    /**
     * Using the Todos service to process user, todos combinations data
     */
    @Autowired
    private TodosService todosService;

    @Autowired
    private UserRepository userrepos;

    @PostMapping(value = "/user/{id}", consumes = {"application/json"})
    public ResponseEntity<?> addTodo(@Valid @RequestBody Todos newtodo,
                                     @PathVariable long id)
    {
        User user = userrepos.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "User " + id + " Not Found."));
        newtodo.setTodoid(0);
        newtodo.setUser(user);
        todosService.save(newtodo);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Given the todo id, mark the task as complete
     * <br>Example: <a href="http://localhost:2019/todos/todo/7">http://localhost:2019/todos/todo/7</a>
     *
     * @param todoid The todo to be marked complete
     * @return Status of OK
     */
    @PatchMapping(value = "/todo/{todoid}")
    public ResponseEntity<?> completeTodo(@PathVariable long todoid)
    {
        todosService.markComplete(todoid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
