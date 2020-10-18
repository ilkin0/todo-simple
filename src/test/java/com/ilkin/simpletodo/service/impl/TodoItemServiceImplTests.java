package com.ilkin.simpletodo.service.impl;

import com.ilkin.simpletodo.models.TodoItem;
import com.ilkin.simpletodo.repo.TodoItemRepo;
import com.ilkin.simpletodo.services.TodoItemService;
import com.ilkin.simpletodo.services.impl.TodoItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TodoItemServiceImplTests {

    @Mock
    private TodoItemService itemService;
    private TodoItemRepo itemRepo;

    private TodoItem passed;
    private TodoItem expected;

    private List<TodoItem> expectedList;

    @BeforeEach
    public void init() {

        itemRepo = mock(TodoItemRepo.class);
        itemService = spy(new TodoItemServiceImpl(itemRepo));

        passed = new TodoItem();
        passed.setItemId(1);
        passed.setListId(UUID.randomUUID());
        passed.setTaskName("name");

        expected.setListId(passed.getListId());
        expected.setTaskName(passed.getTaskName());
        expected.setItemId(passed.getItemId());

        expectedList = new ArrayList<>();
        expectedList.add(expected);
    }

    @Test
    @DisplayName("getAllByListId(listId)")
    public void get_all_list_by_id_when_exist() {

        List<TodoItem> todoItemList = new ArrayList<>();
        todoItemList.add(expected);

        when(itemRepo.findAll()).thenReturn(expectedList);

        List<TodoItem> allByListId = itemService.getAllByListId(UUID.randomUUID());

        assertEquals(todoItemList, allByListId);

    }

//    @Test
//    @DisplayName("getAllByListId(Long.Max)")
//    public void get_all_list_by_id_when_not_exist(){
//        when(itemRepo.findAll()).thenReturn(expectedList);
//
//        when(itemService.getAllByListId(UUID.fromString("aa")))
//    }

}
