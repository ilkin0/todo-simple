package com.ilkin.simpletodo.repo;

import com.ilkin.simpletodo.models.TodoItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("TodoItemRepository")
public interface TodoItemRepo extends CrudRepository<TodoItem, Long> {
}
