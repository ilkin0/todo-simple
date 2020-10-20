package com.ilkin.simpletodo.services.impl;

import com.ilkin.simpletodo.exception.generic.EntityCouldNotBeDeletedException;
import com.ilkin.simpletodo.exception.generic.EntityNotFoundException;
import com.ilkin.simpletodo.models.TodoItem;
import com.ilkin.simpletodo.repo.TodoItemRepo;
import com.ilkin.simpletodo.services.TodoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoItemServiceImpl implements TodoItemService {

    private final TodoItemRepo itemRepo;

    @Override
    public List<TodoItem> getAllByListId(UUID listId) {
        return itemRepo.findByListId(listId);
    }

    @Override
    public TodoItem getItemById(long id) throws EntityNotFoundException {

        Optional<TodoItem> byItemId = itemRepo.findById(id);

        return byItemId.orElseThrow(() -> new EntityNotFoundException(TodoItem.class, id));
    }

    @Override
    public TodoItem saveItem(TodoItem todoItem) {
        return itemRepo.save(todoItem);
    }

    @Override
    public TodoItem deleteById(long id) throws EntityNotFoundException {

        TodoItem itemById = this.getItemById(id);

        try {
            itemRepo.delete(itemById);
            return itemById;
        } catch (Exception e) {
            throw new EntityCouldNotBeDeletedException(e, TodoItem.class, id);
        }
    }

    @Override
    public TodoItem updateItem(TodoItem editedItem) {

        TodoItem todoItem = itemRepo.findById(editedItem.getItemId()).orElse(null);

        if (todoItem != null) {
            todoItem.setTaskName(editedItem.getTaskName());
            return itemRepo.save(todoItem);
        }

        return itemRepo.save(todoItem);
    }

    @Override
    public TodoItem changeDoneStatus(long id) {

        TodoItem todoItem = itemRepo.findById(id).orElse(null);

        if (todoItem != null) {
            todoItem.setDone(!todoItem.isDone());
            itemRepo.save(todoItem);
            return todoItem;
        }


        return null;
    }
}
