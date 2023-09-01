package com.app.service;

import com.app.model.User;

public interface UserService {

	public User createUser(User user);
	
	public boolean checkEmail(String email);
}
