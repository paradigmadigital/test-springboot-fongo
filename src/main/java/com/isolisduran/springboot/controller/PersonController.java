package com.isolisduran.springboot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.isolisduran.springboot.bean.Person;
import com.isolisduran.springboot.service.PersonService;

@RequestMapping("/api")
@RestController
public class PersonController {

	@Autowired
	private PersonService personService;

	@RequestMapping(method = RequestMethod.GET, value = "/person/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Person> getById(@PathVariable int id) {
		Person person = personService.getById(id);
		return new ResponseEntity<>(person, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/person", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> create(@Valid @RequestBody Person person) {
		Person newPerson = personService.create(person);
		return new ResponseEntity<String>(String.valueOf(newPerson.getId()), HttpStatus.CREATED);
	}
}
