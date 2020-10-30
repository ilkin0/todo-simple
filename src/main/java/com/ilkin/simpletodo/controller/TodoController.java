package com.ilkin.simpletodo.controller;

import com.ilkin.simpletodo.exception.generic.EntityNotFoundException;
import com.ilkin.simpletodo.models.TodoItem;
import com.ilkin.simpletodo.services.TodoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ilkin.simpletodo.constant.Constant.TODO_ITEM_URL;

@RestController
@RequestMapping(TODO_ITEM_URL)
@RequiredArgsConstructor
public class TodoController {

    private final TodoItemService itemService;

    @GetMapping()
    public List<TodoItem> findAll() {
        return itemService.getAllItems();
    }

    @GetMapping("{itemId}")
    public TodoItem findByItemId(
            @PathVariable("itemId") long id
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
            @PathVariable long itemId,
            @RequestBody TodoItem todoItem
    ) throws EntityNotFoundException {
        return itemService.updateItem(itemId, todoItem);
    }

    @DeleteMapping("{itemId}")
    public TodoItem deleteItem(
            @PathVariable("itemId") long id
    ) throws EntityNotFoundException {
        return itemService.deleteItemById(id);
    }

    @PostMapping("{itemId}")
    public TodoItem changeItemStatus(
            @PathVariable("itemId") long id
    ) {
        return itemService.changeDoneStatus(id);
    }
}
