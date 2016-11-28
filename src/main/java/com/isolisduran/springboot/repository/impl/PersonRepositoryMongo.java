package com.isolisduran.springboot.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.isolisduran.springboot.bean.Person;
import com.isolisduran.springboot.repository.PersonRepository;

@Repository
public class PersonRepositoryMongo implements PersonRepository{

	private final MongoOperations mongoOperations;
	
	@Autowired
	public PersonRepositoryMongo(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
	}
	
	@Override
	public Person findOne(int id) {
		return mongoOperations.findOne(new Query(Criteria.where("id").is(id)), Person.class);
	}

	@Override
	public Person save(Person person) {
		mongoOperations.save(person);
		return person;
	}

}
