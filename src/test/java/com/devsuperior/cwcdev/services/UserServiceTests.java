package com.devsuperior.cwcdev.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.cwcdev.dto.UserDTO;
import com.devsuperior.cwcdev.entities.User;
import com.devsuperior.cwcdev.projetctions.UserDetailsProjection;
import com.devsuperior.cwcdev.repositories.UserRepository;
import com.devsuperior.cwcdev.tests.UserDetailsFactory;
import com.devsuperior.cwcdev.tests.UserFactory;
import com.devsuperior.cwcdev.util.CustomUserUtil;


@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class UserServiceTests {

	@InjectMocks
	private UserService service;

	@Mock
	private UserRepository userRepository;

	@Mock
	private CustomUserUtil userUtil;

	private String existingUsername, nonExistingUsername;
	private User user;
	private List<UserDetailsProjection> userDetails;

	@BeforeEach
	void setUp() throws Exception {
		existingUsername = "calebewerneck@hotmail.com";
		nonExistingUsername = "email@gmail.com";
		user = UserFactory.createUserEntity();

		userDetails = UserDetailsFactory.createCustomAdminUser(existingUsername);

		Mockito.when(userRepository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
		Mockito.when(userRepository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(new ArrayList<>());

		Mockito.when(userRepository.findByEmail(existingUsername)).thenReturn(Optional.of(user));
		Mockito.when(userRepository.findByEmail(nonExistingUsername)).thenReturn(Optional.empty());
	}

	@Test
	public void authenticatedShouldReturnUserEntityWhenUserExists() {
		Mockito.when(userUtil.getLoggedUsername()).thenReturn(existingUsername);
		User result = service.authenticated();

		Assertions.assertNotNull(result);
		Assertions.assertEquals(existingUsername, result.getUsername());
	}

	@Test
	public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
		Mockito.doThrow(ClassCastException.class).when(userUtil).getLoggedUsername();
		Assertions.assertThrows(UsernameNotFoundException.class, () -> service.authenticated());
	}

	@Test
	public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
		UserDetails result = service.loadUserByUsername(existingUsername);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(existingUsername, result.getUsername());
	}

	@Test
	public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername(nonExistingUsername);
		});
	}
	
	@Test
	public void getMeShouldReturnUserDTOWhenUserIsAuthenticated() {
	    // Simular o comportamento do método authenticated()
	    Mockito.when(userUtil.getLoggedUsername()).thenReturn(existingUsername);
	    Mockito.when(userRepository.findByEmail(existingUsername)).thenReturn(Optional.of(user));

	    // Executar o método getMe()
	    UserDTO result = service.getMe();

	    // Verificar se o retorno não é nulo e se os dados estão corretos
	    Assertions.assertNotNull(result);
	    Assertions.assertEquals(existingUsername, result.getEmail());
	}

}