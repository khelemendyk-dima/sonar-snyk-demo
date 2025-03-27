package com.example.demo.sonardemo.controller;

import com.example.demo.sonardemo.model.User;
import com.example.demo.sonardemo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(1L, "JohnDoe", "john@example.com", "password123");
        user2 = new User(2L, "JaneDoe", "jane@example.com", "password456");
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        ResponseEntity<List<User>> response = userController.getAllUsers(null);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetAllUsersByName() {
        when(userService.getAllUsersByUsername("JohnDoe")).thenReturn(List.of(user1));

        ResponseEntity<List<User>> response = userController.getAllUsers("JohnDoe");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        verify(userService, times(1)).getAllUsersByUsername("JohnDoe");
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById(1L)).thenReturn(user1);

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("JohnDoe", Objects.requireNonNull(response.getBody()).getUsername());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userService.getUserById(99L)).thenReturn(null);

        ResponseEntity<User> response = userController.getUserById(99L);

        assertEquals(200, response.getStatusCode().value());
        assertNull(response.getBody());
        verify(userService, times(1)).getUserById(99L);
    }

    @Test
    void testCreateUser() {
        doNothing().when(userService).createUser(user1);

        ResponseEntity<Void> response = userController.createUser(user1);

        assertEquals(201, response.getStatusCode().value());
        verify(userService, times(1)).createUser(user1);
    }

    @Test
    void testUpdateUser() {
        doNothing().when(userService).updateUser(eq(1L), any(User.class));

        ResponseEntity<Void> response = userController.updateUser(1L, user1);

        assertEquals(200, response.getStatusCode().value());
        verify(userService, times(1)).updateUser(eq(1L), any(User.class));
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(userService, times(1)).deleteUser(1L);
    }
}
