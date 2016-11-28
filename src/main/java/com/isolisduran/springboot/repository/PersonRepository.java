package com.isolisduran.springboot.repository;

import com.isolisduran.springboot.bean.Person;

public interface PersonRepository {

	Person findOne(int id);
	
	Person save(Person person);
	
	
}
