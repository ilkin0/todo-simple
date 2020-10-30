package com.ilkin.simpletodo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ilkin.simpletodo.TestsConfiguration;
import com.ilkin.simpletodo.exception.generic.EntityNotFoundException;
import com.ilkin.simpletodo.models.TodoItem;
import com.ilkin.simpletodo.services.TodoItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ilkin.simpletodo.constant.Constant.TODO_ITEM_URL;
import static com.ilkin.simpletodo.utils.Utils.getJsonString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        MockitoAnnotations.initMocks(this);

        requestObject = new TodoItem();
        requestObject.setDone(false);
        requestObject.setTaskName("task");
        requestObject.setCreatedTime(LocalDateTime.now());
        requestObject.setUpdateTime(LocalDateTime.now());

        requestBody = getJsonString(requestObject);

        responseObject = new TodoItem();
        responseObject.setUpdateTime(requestObject.getUpdateTime());
        responseObject.setCreatedTime(requestObject.getCreatedTime());
        responseObject.setTaskName(requestObject.getTaskName());
        responseObject.setDone(requestObject.isDone());
        responseObject.setItemId(1);

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
                get(TODO_ITEM_URL + "1")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseBody));

        verify(itemService).getItemById(1);
    }

    @Test
    @DisplayName("GET " + TODO_ITEM_URL + Long.MAX_VALUE)
    public void find_by_item_id_when_not_exist() throws Exception {

        when(itemService.getItemById(Long.MAX_VALUE)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                get(TODO_ITEM_URL + Long.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());

        verify(itemService).getItemById(Long.MAX_VALUE);
    }


}
