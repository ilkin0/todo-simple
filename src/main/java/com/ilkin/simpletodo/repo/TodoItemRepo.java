package com.ilkin.simpletodo.repo;

import com.ilkin.simpletodo.models.TodoItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("TodoItemRepository")
public interface TodoItemRepo extends CrudRepository<TodoItem, Long> {

    List<TodoItem> findByListId(UUID listId);
}
