package com.example.demo.app.aplication.port.out;

import com.example.demo.app.domain.model.User;

public interface UserPersistencePort {
	/*
	 * 
	 * >>>>>>> IMPLEMENTED BY USER JPA ADAPTER<<<<<<<<<<
	 * 
	 */
    User save(User user);
    
}
