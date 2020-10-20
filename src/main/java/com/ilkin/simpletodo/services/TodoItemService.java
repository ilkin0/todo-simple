package com.ilkin.simpletodo.services;

import com.ilkin.simpletodo.exception.generic.EntityNotFoundException;
import com.ilkin.simpletodo.models.TodoItem;

import java.util.List;
import java.util.UUID;

public interface TodoItemService {

    List<TodoItem> getAllByListId(UUID listId);

    TodoItem getItemById(long id) throws EntityNotFoundException;

    TodoItem saveItem(TodoItem todoItem);

    TodoItem deleteById(long id) throws EntityNotFoundException;

    TodoItem updateItem(TodoItem editedItem);

    TodoItem changeDoneStatus(long id);
}
