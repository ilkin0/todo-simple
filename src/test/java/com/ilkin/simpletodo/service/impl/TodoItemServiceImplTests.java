package com.ilkin.simpletodo.service.impl;

import com.ilkin.simpletodo.exception.generic.EntityNotFoundException;
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
import java.util.Optional;

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
        passed.setTaskName("name");

        expected = new TodoItem();
        expected.setTaskName(passed.getTaskName());
        expected.setItemId(passed.getItemId());

        expectedList = new ArrayList<>();
        expectedList.add(expected);
    }

    @Test
    @DisplayName("getAllByListId()")
    public void get_all_items() {

        List<TodoItem> todoItemList = new ArrayList<>();
        todoItemList.add(expected);

        when(itemRepo.findAll()).thenReturn(expectedList);

        List<TodoItem> allByListId = itemService.getAllItems();

        assertEquals(todoItemList, allByListId);
        //TODO didnt pass

    }


    @Test
    @DisplayName("getItemById(id)")
    public void get_item_by_id_when_exist() throws Exception {

        when(itemRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(expected));

        TodoItem itemById = itemService.getItemById(1);

        assertEquals(expected, itemById);
    }

    @Test
    @DisplayName("getItemById(Long.Max_Value)")
    public void get_item_by_id_when_not_exist() {

        when(itemRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> itemService.getItemById(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("saveItem(TodoItem todoItem)")
    public void save_item() {

        when(itemRepo.save(passed)).thenReturn(expected);

        TodoItem todoItem = itemService.saveItem(passed);

        assertEquals(expected, todoItem);
    }

    @Test
    @DisplayName("deleteItemById(id)")
    public void delete_item_by_id_when_exist() throws EntityNotFoundException {

        when(itemRepo.findById(1L)).thenReturn(Optional.of(expected));

        TodoItem todoItem = itemService.deleteItemById(1L);

        assertEquals(expected, todoItem);
    }

    @Test
    @DisplayName("deleteItemById(Long.Max_Value)")
    public void delete_item_by_id_when_not_exist() throws EntityNotFoundException {

        when(itemRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> itemService.deleteItemById(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("editItem(TodoItem todoItem)")
    public void edit_item() {

        when(itemRepo.findById(1L)).thenReturn(Optional.of(expected));

        TodoItem todoItem = itemService.updateItem(passed);

        assertEquals(expected, todoItem);
        //TODO not passed
    }

    @Test
    @DisplayName("checkIsDone")
    public void change_done_status() {

        when(itemRepo.findById(1L)).thenReturn(Optional.of(expected));

        TodoItem todoItem = itemService.changeDoneStatus(1L);

        assertEquals(expected, todoItem);
    }

}
