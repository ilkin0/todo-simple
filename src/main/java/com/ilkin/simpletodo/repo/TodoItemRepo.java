package com.ilkin.simpletodo.repo;

import com.ilkin.simpletodo.models.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("TodoItemRepository")
public interface TodoItemRepo extends JpaRepository<TodoItem, Long> {

    TodoItem findByItemId(long itemId);

    List<TodoItem> findByListId(UUID listId);
}
