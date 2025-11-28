package com.example.demo.services;

public interface LogoutService {
	public void logout(String session);

	public void logoutByToken(String token);

}
