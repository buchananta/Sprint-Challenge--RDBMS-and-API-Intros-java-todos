package com.lambdaschool.todos.services;

import com.lambdaschool.todos.models.Todos;
import com.lambdaschool.todos.repository.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "todosService")
public class TodosServiceImpl implements TodosService
{
    @Autowired
    private TodosRepository todosrepos;

    @Transactional
    @Override
    public Todos markComplete(long todoid) {
        Todos newTodo = todosrepos.findById(todoid)
            .orElseThrow(() -> new EntityNotFoundException(
                "Todo id " + todoid + " Not Found."    
            ));
            newTodo.setCompleted(true);
        return todosrepos.save(newTodo);
    }

    @Transactional
    @Override
    public Todos save(Todos todo)
    {
        Todos newTodo = new Todos();
        if (todo.getTodoid() != 0)
        {
            todosrepos.findById(todo.getTodoid())
                    .orElseThrow(() -> new EntityNotFoundException("Todo " + todo.getTodoid() + " Not Found! "));
            newTodo.setTodoid(todo.getTodoid());
        }

        newTodo.setTodoid(todo.getTodoid());
        newTodo.setDescription(todo.getDescription());
        newTodo.setCompleted(todo.isCompleted());
        newTodo.setUser(todo.getUser());

        return todosrepos.save(newTodo);
    }
}
