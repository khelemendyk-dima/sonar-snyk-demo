package com.example.demo.sonardemo.service;

import com.example.demo.sonardemo.model.User;
import com.example.demo.sonardemo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "JohnDoe", "john@example.com", "password123");
    }

    @Test
    void testGetAllUsersByUsername() {
        String usernameToFind = "JohnDoe";
        when(userRepository.getAllUsersByUsername(usernameToFind)).thenReturn(List.of(user));
        List<User> users = userService.getAllUsersByUsername(usernameToFind);
        assertEquals(1, users.size());
        assertEquals(usernameToFind, users.getFirst().getUsername());
        verify(userRepository, times(1)).getAllUsersByUsername(usernameToFind);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.getAllUsers()).thenReturn(List.of(user));
        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
        verify(userRepository, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.getUserById(1L)).thenReturn(user);
        User foundUser = userService.getUserById(1L);
        assertNotNull(foundUser);
        assertEquals("JohnDoe", foundUser.getUsername());
    }

    @Test
    void testCreateUser() {
        doNothing().when(userRepository).createUser(user);
        userService.createUser(user);
        verify(userRepository, times(1)).createUser(user);
    }

    @Test
    void testUpdateUser() {
        doNothing().when(userRepository).updateUser(1L, user);
        userService.updateUser(1L, user);
        verify(userRepository, times(1)).updateUser(1L, user);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteUser(1L);
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteUser(1L);
    }
}
