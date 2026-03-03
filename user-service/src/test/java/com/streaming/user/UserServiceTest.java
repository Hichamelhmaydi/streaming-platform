package com.streaming.user;

import com.streaming.user.dto.UserRequest;
import com.streaming.user.dto.UserResponse;
import com.streaming.user.entity.User;
import com.streaming.user.exception.UserAlreadyExistsException;
import com.streaming.user.exception.UserNotFoundException;
import com.streaming.user.mapper.UserMapper;
import com.streaming.user.repository.UserRepository;
import com.streaming.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @InjectMocks private UserServiceImpl userService;

    private User user;
    private UserRequest request;
    private UserResponse response;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).username("johndoe").email("john@example.com").password("secret123").build();
        request = UserRequest.builder().username("johndoe").email("john@example.com").password("secret123").build();
        response = UserResponse.builder().id(1L).username("johndoe").email("john@example.com").build();
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        when(userRepository.existsByUsername("johndoe")).thenReturn(false);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(userMapper.toEntity(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        UserResponse result = userService.createUser(request);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("johndoe");
        verify(userRepository).save(user);
    }

    @Test
    void createUser_WhenUsernameTaken_ShouldThrow() {
        when(userRepository.existsByUsername("johndoe")).thenReturn(true);
        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void createUser_WhenEmailTaken_ShouldThrow() {
        when(userRepository.existsByUsername("johndoe")).thenReturn(false);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(true);
        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void getUserById_WhenExists_ShouldReturn() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(response);

        UserResponse result = userService.getUserById(1L);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getUserById_WhenNotExists_ShouldThrow() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUserById(999L))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void getAllUsers_ShouldReturnList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(userMapper.toResponse(user)).thenReturn(response);
        List<UserResponse> results = userService.getAllUsers();
        assertThat(results).hasSize(1);
    }

    @Test
    void deleteUser_WhenExists_ShouldDelete() {
        when(userRepository.existsById(1L)).thenReturn(true);
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_WhenNotExists_ShouldThrow() {
        when(userRepository.existsById(999L)).thenReturn(false);
        assertThatThrownBy(() -> userService.deleteUser(999L))
                .isInstanceOf(UserNotFoundException.class);
    }
}
