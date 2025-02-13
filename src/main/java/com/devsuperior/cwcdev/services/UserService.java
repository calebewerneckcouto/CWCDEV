package com.devsuperior.cwcdev.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.cwcdev.dto.UserDTO;
import com.devsuperior.cwcdev.entities.Role;
import com.devsuperior.cwcdev.entities.User;
import com.devsuperior.cwcdev.projetctions.UserDetailsProjection;
import com.devsuperior.cwcdev.repositories.UserRepository;
import com.devsuperior.cwcdev.util.CustomUserUtil;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private CustomUserUtil userUtil;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
		if (result.size() == 0) {
			throw new UsernameNotFoundException("Email not found");
		}

		User user = new User();
		user.setEmail(result.get(0).getUsername());
		user.setPassword(result.get(0).getPassword());
		for (UserDetailsProjection projection : result) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}

		return user;
	}

	protected User authenticated() {
		try {
			
			String username = userUtil.getLoggedUsername();
			return repository.findByEmail(username).get();
		} catch (Exception e) {
			throw new UsernameNotFoundException("Invalid user");
		}
	}

	@Transactional(readOnly = true)
	public UserDTO getMe() {
		User entity = authenticated();
		return new UserDTO(entity);
	}
}
