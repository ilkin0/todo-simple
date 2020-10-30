package com.ilkin.simpletodo.services;

import com.ilkin.simpletodo.exception.generic.EntityNotFoundException;
import com.ilkin.simpletodo.models.TodoItem;

import java.util.List;
import java.util.UUID;

public interface TodoItemService {

    List<TodoItem> getAllItems();

    TodoItem getItemById(long id) throws EntityNotFoundException;

    TodoItem saveItem(TodoItem todoItem);

    TodoItem deleteItemById(long id) throws EntityNotFoundException;

    TodoItem updateItem(long id, TodoItem editedItem) throws EntityNotFoundException;

    TodoItem changeDoneStatus(long id);
}
