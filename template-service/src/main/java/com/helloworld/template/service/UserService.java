package com.helloworld.template.service;

import com.helloworld.template.entity.User;

public interface UserService {
	
	public User login(User user);
	
	public void logout(User user);

}
