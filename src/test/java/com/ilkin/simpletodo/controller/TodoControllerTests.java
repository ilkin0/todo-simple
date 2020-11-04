package com.ilkin.simpletodo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ilkin.simpletodo.TestsConfiguration;
import com.ilkin.simpletodo.exception.generic.EntityNotFoundException;
import com.ilkin.simpletodo.models.TodoItem;
import com.ilkin.simpletodo.services.TodoItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.ilkin.simpletodo.constant.Constant.TODO_ITEM_URL;
import static com.ilkin.simpletodo.utils.Utils.getJsonString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TodoControllerTests extends TestsConfiguration {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TodoItemService itemService;

    private TodoItem requestObject;
    private String requestBody;

    private TodoItem responseObject;
    private String responseBody;

    private List<TodoItem> responseObjectList;
    private String responseBodyList;

    @BeforeEach
    public void init() throws JsonProcessingException {

//        mockMvc = standaloneSetup(new TodoController(itemService)).build();

        requestObject = new TodoItem();
        requestObject.setItemId(1);
        requestObject.setDone(false);
        requestObject.setTaskName("task");
        requestBody = getJsonString(requestObject);

        responseObject = new TodoItem();
        responseObject.setItemId(1);
        responseObject.setItemId(requestObject.getItemId());
        responseObject.setUpdateTime(requestObject.getUpdateTime());
        responseObject.setCreatedTime(requestObject.getCreatedTime());
        responseObject.setTaskName(requestObject.getTaskName());
        responseObject.setDone(requestObject.isDone());

        responseBody = getJsonString(responseObject);

        responseObjectList = new ArrayList<>();
        responseObjectList.add(responseObject);

        responseBodyList = getJsonString(responseObjectList);
    }

    @Test
    @DisplayName("GET " + TODO_ITEM_URL)
    public void find_all() throws Exception {

        when(itemService.getAllItems()).thenReturn(responseObjectList);

        mockMvc.perform(
                get(TODO_ITEM_URL)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(responseBodyList));

        verify(itemService).getAllItems();
    }

    @Test
    @DisplayName("GET " + TODO_ITEM_URL + "/itemId")
    public void find_by_item_id_when_exist() throws Exception {

        when(itemService.getItemById(1)).thenReturn(responseObject);

        mockMvc.perform(
                get(TODO_ITEM_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseBody));

        verify(itemService).getItemById(1);
    }

    @Test
    @DisplayName("GET " + TODO_ITEM_URL + "/" + Long.MAX_VALUE)
    public void find_by_item_id_when_not_exist() throws Exception {

        when(itemService.getItemById(Long.MAX_VALUE)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                get(TODO_ITEM_URL + "/" + Long.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());

        verify(itemService).getItemById(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("POST " + TODO_ITEM_URL)
    public void save_item() throws Exception {

        when(itemService.saveItem(responseObject)).thenReturn(responseObject);

        mockMvc.perform(
                post(TODO_ITEM_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseBody));

        verify(itemService).saveItem(responseObject);
    }

    @Test
    @DisplayName("PUT " + TODO_ITEM_URL + "/itemId")
    public void update_item_when_id_exist() throws Exception {

        when(itemService.updateItem(1, requestObject)).thenReturn(responseObject);

        mockMvc.perform(
                put(TODO_ITEM_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseBody));

        verify(itemService).updateItem(1, requestObject);
    }

    @Test
    @DisplayName("PUT " + TODO_ITEM_URL + "/" + Long.MAX_VALUE)
    public void update_item_when_id_not_exist() throws Exception {

        when(itemService.updateItem(Long.MAX_VALUE, requestObject)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                put(TODO_ITEM_URL + "/" + Long.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
                .andExpect(status().isNotFound());

        verify(itemService).updateItem(Long.MAX_VALUE, requestObject);
    }

    @Test
    @DisplayName("DELETE " + TODO_ITEM_URL + "/1")
    public void delete_item_when_id_exist() throws Exception {

        when(itemService.deleteItemById(1)).thenReturn(responseObject);

        mockMvc.perform(
                delete(TODO_ITEM_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseBody));

        verify(itemService).deleteItemById(1);
    }

    @Test
    @DisplayName("DELETE " + TODO_ITEM_URL + "/" + Long.MAX_VALUE)
    public void delete_item_when_id_not_exist() throws Exception {

        when(itemService.deleteItemById(Long.MAX_VALUE)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                delete(TODO_ITEM_URL + "/" + Long.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
                .andExpect(status().isNotFound());

        verify(itemService).deleteItemById(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("POST " + TODO_ITEM_URL + "/1")
    public void change_item_status_when_id_exist() throws Exception {

        when(itemService.changeItemStatus(1)).thenReturn(responseObject);

        mockMvc.perform(
                post(TODO_ITEM_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseBody));

        verify(itemService).changeItemStatus(1);
    }

    @Test
    @DisplayName("POST " + TODO_ITEM_URL + "/" + Long.MAX_VALUE)
    public void change_item_status_when_id_not_exist() throws Exception {

        when(itemService.changeItemStatus(Long.MAX_VALUE)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                post(TODO_ITEM_URL + "/" + Long.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
                .andExpect(status().isNotFound());

        verify(itemService).changeItemStatus(Long.MAX_VALUE);
    }
}
