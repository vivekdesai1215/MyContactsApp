package com.authentication;

import com.userRegistration.User;
import com.userRegistration.UserType;

public interface Authentication {
	User authenticate(String username, String password);
}
