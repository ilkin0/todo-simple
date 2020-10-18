package com.ilkin.simpletodo.services;

import com.ilkin.simpletodo.models.TodoItem;

import java.util.List;
import java.util.UUID;

public interface TodoItemService {
    List<TodoItem> getAllByListId(UUID listId);

}
