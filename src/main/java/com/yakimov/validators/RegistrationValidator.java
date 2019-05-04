package com.yakimov.validators;

import java.util.ArrayList;
import java.util.List;

public class RegistrationValidator {
	//TODO Extend validations
	public static List<String> validate(String login, String password, String confirmation) {
		List<String> violations = new ArrayList<>(3);
		if (login.length() < 3) {
			violations.add("Login too short");
		}
		if (password.length() < 8) {
			violations.add("Password too short");
		}
		if (!password.equals(confirmation)) {
			violations.add("Password and password confirmation doesn`t match");
		}
		return violations;
	}
	
}
