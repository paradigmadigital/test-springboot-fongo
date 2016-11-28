package com.isolisduran.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isolisduran.springboot.bean.Person;
import com.isolisduran.springboot.repository.PersonRepository;
import com.isolisduran.springboot.service.PersonService;

@Service
public class PersonServiceDefault implements PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public Person getById(int id) {
		return personRepository.findOne(id);
	}

	@Override
	public Person create(Person person) {
		return personRepository.save(person);
	}

}
