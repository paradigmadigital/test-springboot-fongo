package com.isolisduran.springboot.service;

import com.isolisduran.springboot.bean.Person;

public interface PersonService {

	public Person getById(int id);
	
	public Person create(Person person);
	
}
