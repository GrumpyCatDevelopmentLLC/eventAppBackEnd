package com.tiy.hackathon;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Brett on 9/29/16.
 */
public interface UserRepository extends CrudRepository <User, Integer> {

	User findFirstByEmail (String email);
	User findFirstByEmailAndPassword (String email, String password);

}
