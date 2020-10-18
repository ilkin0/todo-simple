package com.ilkin.simpletodo.services.impl;

import com.ilkin.simpletodo.models.TodoItem;
import com.ilkin.simpletodo.repo.TodoItemRepo;
import com.ilkin.simpletodo.services.TodoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoItemServiceImpl implements TodoItemService {

    private final TodoItemRepo itemRepo;

    @Override
    public List<TodoItem> getAllByListId(UUID listId) {
        return itemRepo.findByListId(listId);
    }
}
