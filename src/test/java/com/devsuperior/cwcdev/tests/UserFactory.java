package com.devsuperior.cwcdev.tests;

import java.time.LocalDate;

import com.devsuperior.cwcdev.entities.User;

public class UserFactory {
	
	public static User createUserEntity() {
		User user = new User(1L, "Calebe Werneck Couto", "calebewerneck@hotmail.com", "31987967617", LocalDate.of(1988, 4, 10), "$2a$10$BfQ6lnNL22CPk91XstWDbOQ0ETzdXV43X4ep5rKI9AM0NWMa90BT2");
		return user;
	}
}
