//package com.example.demo.sonardemo.repository;
//
//import com.example.demo.sonardemo.exception.UserRepositoryException;
//import com.example.demo.sonardemo.model.User;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import java.sql.*;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class UserRepositoryTest {
//
//    @Mock
//    private Connection connection;
//
//    @Mock
//    private PreparedStatement preparedStatement;
//
//    @Mock
//    private Statement statement;
//
//    @Mock
//    private ResultSet resultSet;
//
//    @InjectMocks
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp() throws SQLException {
//        MockitoAnnotations.openMocks(this);
//
//        when(connection.createStatement()).thenReturn(statement);
//        when(statement.executeQuery(any())).thenReturn(resultSet);
//        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
//        when(preparedStatement.executeQuery(any())).thenReturn(resultSet);
//
//        mockStatic(DriverManager.class);
//        when(DriverManager.getConnection(any(String.class), any(String.class), any(String.class)))
//                .thenReturn(connection);
//    }
//
//    @AfterEach
//    void tearDown() {
//        Mockito.clearAllCaches();
//    }
//
//    @Test
//    void testGetAllUsersByUsername() throws SQLException {
//        String username = "testUser";
//
//        when(resultSet.next()).thenReturn(true, false);
//        when(resultSet.getLong("id")).thenReturn(1L);
//        when(resultSet.getString("username")).thenReturn("testUser");
//        when(resultSet.getString("email")).thenReturn("test@example.com");
//        when(resultSet.getString("password")).thenReturn("encryptedPassword");
//
//        List<User> users = userRepository.getAllUsersByUsername(username);
//
//        assertEquals(1, users.size());
//        assertEquals("testUser", users.getFirst().getUsername());
//    }
//
//    @Test
//    void testGetAllUsers() throws SQLException {
//        when(resultSet.next()).thenReturn(true, false);
//        when(resultSet.getLong("id")).thenReturn(1L);
//        when(resultSet.getString("username")).thenReturn("testUser");
//        when(resultSet.getString("email")).thenReturn("test@example.com");
//        when(resultSet.getString("password")).thenReturn("encryptedPassword");
//
//        List<User> users = userRepository.getAllUsers();
//
//        assertEquals(1, users.size());
//        assertEquals("testUser", users.getFirst().getUsername());
//    }
//
//    @Test
//    void testGetUserById() throws SQLException {
//        Long userId = 1L;
//
//        when(resultSet.next()).thenReturn(true, false);
//        when(resultSet.getLong("id")).thenReturn(userId);
//        when(resultSet.getString("username")).thenReturn("testUser");
//        when(resultSet.getString("email")).thenReturn("test@example.com");
//        when(resultSet.getString("password")).thenReturn("encryptedPassword");
//
//        User user = userRepository.getUserById(userId);
//
//        assertNotNull(user);
//        assertEquals("testUser", user.getUsername());
//        assertEquals("test@example.com", user.getEmail());
//    }
//
//    @Test
//    void testCreateUser() throws SQLException {
//        User user = new User(1L, "testUser", "test@example.com", "encryptedPassword");
//
//        when(preparedStatement.executeUpdate()).thenReturn(1);
//
//        userRepository.createUser(user);
//
//        verify(preparedStatement).setString(1, user.getUsername());
//        verify(preparedStatement).setString(2, user.getEmail());
//    }
//
//    @Test
//    void testUpdateUser() throws SQLException {
//        Long userId = 1L;
//        User user = new User(userId, "updatedUser", "updated@example.com", "updatedPassword");
//
//        when(preparedStatement.executeUpdate()).thenReturn(1);
//
//        userRepository.updateUser(userId, user);
//
//        verify(preparedStatement).setString(1, user.getUsername());
//        verify(preparedStatement).setString(2, user.getEmail());
//        verify(preparedStatement).setLong(4, user.getId());
//    }
//
//    @Test
//    void testDeleteUser() throws SQLException {
//        long userId = 1L;
//
//        when(preparedStatement.executeUpdate()).thenReturn(1);
//
//        userRepository.deleteUser(userId);
//
//        verify(preparedStatement).setLong(1, userId);
//    }
//
//    @Test
//    void testGetAllUsersByUsername_ShouldThrowUserRepositoryException_WhenSQLExceptionOccurs() throws SQLException {
//        String username = "testUser";
//
//        when(DriverManager.getConnection(any(), any(), any())).thenThrow(SQLException.class);
//
//        assertThrows(UserRepositoryException.class, () -> userRepository.getAllUsersByUsername(username));
//    }
//
//    @Test
//    void testGetAllUsers_ShouldThrowUserRepositoryException_WhenSQLExceptionOccurs() throws SQLException {
//        when(DriverManager.getConnection(any(), any(), any())).thenThrow(SQLException.class);
//
//        assertThrows(UserRepositoryException.class, () -> userRepository.getAllUsers());
//    }
//
//    @Test
//    void testGetUserById_ShouldThrowUserRepositoryException_WhenSQLExceptionOccurs() throws SQLException {
//        Long userId = 1L;
//
//        when(DriverManager.getConnection(any(), any(), any())).thenThrow(SQLException.class);
//
//        assertThrows(UserRepositoryException.class, () -> userRepository.getUserById(userId));
//    }
//
//    @Test
//    void testCreateUser_ShouldThrowUserRepositoryException_WhenSQLExceptionOccurs() throws SQLException {
//        User user = new User(1L, "testUser", "test@example.com", "password");
//
//        when(DriverManager.getConnection(any(), any(), any())).thenThrow(SQLException.class);
//
//        assertThrows(UserRepositoryException.class, () -> userRepository.createUser(user));
//    }
//
//    @Test
//    void testUpdateUser_ShouldThrowUserRepositoryException_WhenSQLExceptionOccurs() throws SQLException {
//        Long userId = 1L;
//        User user = new User(userId, "testUser", "test@example.com", "password");
//
//        when(DriverManager.getConnection(any(), any(), any())).thenThrow(SQLException.class);
//
//        assertThrows(UserRepositoryException.class, () -> userRepository.updateUser(userId, user));
//    }
//
//    @Test
//    void testDeleteUser_ShouldThrowUserRepositoryException_WhenSQLExceptionOccurs() throws SQLException {
//        Long userId = 1L;
//
//        when(DriverManager.getConnection(any(), any(), any())).thenThrow(SQLException.class);
//
//        assertThrows(UserRepositoryException.class, () -> userRepository.deleteUser(userId));
//    }
//}
