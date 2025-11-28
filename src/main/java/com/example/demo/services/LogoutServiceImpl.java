package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.example.demo.repositories.UserTokenRepository;

@Service
public class LogoutServiceImpl implements LogoutService{

	@Autowired
	private UserTokenRepository tokenRepository;

	@Override
	public void logout(String session) {

		tokenRepository.deleteBySessionId(session);

	}

	@Override
	public void logoutByToken(String token) {
		tokenRepository.deleteByToken(token);
	}

}
