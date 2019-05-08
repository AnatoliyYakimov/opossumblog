package com.yakimov.validators;

import java.util.ArrayList;
import java.util.List;

public class RegistrationValidator {
    private static String LOGIN_REGEX = "[\\S]+{3,20}";
    private static String PASSWORD_REGEX = "[\\S]+{8,20}";
	//TODO Extend validations
	public static List<String> validateLogin(String login) {
		List<String> violations = new ArrayList<>(3);
		if (login.length() < 3) {
			violations.add("Login too short");
		}
		if (!login.matches(LOGIN_REGEX)) {
		    violations.add("Login contains the forbidden symbols");
		}
		return violations;
	}
	
	public static List<String> validatePasswordAndConfirmation(String pass, String conf){
	    List<String> violations = new ArrayList<>(3);
	    if (pass.length() < 8) {
	      violations.add("Password too short");
	    }
	    if (!pass.equals(conf)) {
	      violations.add("Password and password confirmation doesn`t match");
	    }
	    if (!pass.matches(PASSWORD_REGEX)) {
	        violations.add("Password contains the forbidden symbols");
	    }
	    return violations;
	}
	
}
