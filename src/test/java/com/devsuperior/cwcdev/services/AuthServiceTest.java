package com.devsuperior.cwcdev.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devsuperior.cwcdev.Exceptions.ForbiddenException;
import com.devsuperior.cwcdev.entities.Role;
import com.devsuperior.cwcdev.entities.User;

@ExtendWith(MockitoExtension.class) // Habilita Mockito no JUnit 5
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserService userService;

    private User adminUser;
    private User normalUser;

    @BeforeEach
    void setUp() {
        adminUser = new User();
        adminUser.setId(1L);
        adminUser.getRoles().add(new Role("ROLE_ADMIN")); // Add a Role object

        normalUser = new User();
        normalUser.setId(2L);
    }

    @Test
    void shouldAllowAccessWhenUserIsAdmin() {
        when(userService.authenticated()).thenReturn(adminUser);

        assertDoesNotThrow(() -> authService.validateSelfOrAdmin(3L)); // Deve permitir acesso
    }

    @Test
    void shouldAllowAccessWhenUserIsSelf() {
        when(userService.authenticated()).thenReturn(normalUser);

        assertDoesNotThrow(() -> authService.validateSelfOrAdmin(2L)); // Deve permitir acesso
    }

    @Test
    void shouldDenyAccessWhenUserIsNotAdminAndNotSelf() {
        when(userService.authenticated()).thenReturn(normalUser);

        ForbiddenException exception = assertThrows(ForbiddenException.class, 
            () -> authService.validateSelfOrAdmin(99L)); // Deve lançar exceção

        assertEquals("Acess denied.Should be self or admin", exception.getMessage());
    }
}
