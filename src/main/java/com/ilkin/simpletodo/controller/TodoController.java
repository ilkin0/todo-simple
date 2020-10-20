package com.ilkin.simpletodo.controller;

import com.ilkin.simpletodo.constant.Constant;
import com.ilkin.simpletodo.exception.generic.EntityNotFoundException;
import com.ilkin.simpletodo.models.TodoItem;
import com.ilkin.simpletodo.services.TodoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Constant.API_URL)
@RequiredArgsConstructor
public class TodoController {

    private final TodoItemService itemService;

    @GetMapping()
    public List<TodoItem> findAll(
            @PathVariable("listId") UUID listId
    ) {
        return itemService.getAllByListId(listId);
    }

    @GetMapping("{itemId}")
    public TodoItem findByItemId(
            @PathVariable("id") long id
    ) throws EntityNotFoundException {
        return itemService.getItemById(id);
    }

    @PostMapping
    public TodoItem saveItem(
            @RequestBody TodoItem todoItem
    ) {
        return itemService.saveItem(todoItem);
    }

    @PutMapping("{itemId}")
    public TodoItem updateItem(
            @PathVariable("itemId") long id,
            @RequestBody TodoItem todoItem
    ) {
        return itemService.saveItem(todoItem);
    }

    @DeleteMapping("{itemId}")
    public TodoItem deleteItem(
            @PathVariable("itemId") long id
    ) throws EntityNotFoundException {
        return itemService.deleteById(id);
    }

    @PostMapping("{itemId}")
    public TodoItem changeItemStatus(
            @PathVariable("itemId") long id
    ) {
        return itemService.changeDoneStatus(id);
    }
}
