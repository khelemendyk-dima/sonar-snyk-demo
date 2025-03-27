package com.example.demo.sonardemo.service;

import com.example.demo.sonardemo.model.User;
import com.example.demo.sonardemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsersByUsername(String username) {
        return userRepository.getAllUsersByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public void createUser(User user) {
        userRepository.createUser(user);
    }

    public void updateUser(Long id, User user) {
        userRepository.updateUser(id, user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }
}
