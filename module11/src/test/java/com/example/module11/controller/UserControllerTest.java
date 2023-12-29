package com.example.module11.controller;

import com.example.module11.entity.User;
import com.example.module11.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({UserController.class})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @InjectMocks
    ObjectMapper mapper;

    @Test
    @DisplayName("Поиск всех юзеров")
    void getAllUsersTest() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(
                new User(1L,"Maksim", 29),
                new User(2L, "Alena",  26),
                new User(3L, "Arseniy",  5),
                new User(4L, "Kira",  2)
        ));

        when(service.findAll()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    @DisplayName("Поиск Юзера по id")
    void getUserByIdTest() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(
                new User(1L,"Maksim", 29),
                new User(2L, "Alena",  26),
                new User(3L, "Arseniy",  5),
                new User(4L, "Kira",  2)
        ));
        when(service.findById(4L)).thenReturn(users.get(3));
        mockMvc.perform(get("/api/v1/users/4"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("4"))
                .andExpect(jsonPath("$.name").value("Kira"))
                .andExpect(jsonPath("$.age").value("2"));
    }

    @Test
    @DisplayName("Создание Юзера")
    void createUserTest() throws Exception {
        User user = new User(5L, "Odi", 1);

        when(service.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user))
                )
                .andExpect(header().string("Location", "/api/v1/users/" + user.getId()))
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Изменение Юзера")
    void updateUserTest() throws Exception {
        User user = new User(1L, "Denis", 32);

        when(service.update(any(User.class))).thenReturn(user);

        mockMvc.perform(put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user))
                )
                .andDo(print())
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Удаление Юзера")
    void deleteUserTest() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        mockMvc.perform(delete("/api/v1/users/2"))
                .andDo(print())
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().isOk());
    }
}