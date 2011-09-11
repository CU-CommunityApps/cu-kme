package org.kuali.mobility.people.service;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import org.kuali.mobility.people.entity.Person;
import org.kuali.mobility.people.entity.Search;
import org.springframework.stereotype.Service;

@Service
public class PeopleServiceImpl implements PeopleService {

	@Override
	public List<Person> performSearch(Search search) {

		return null;
	}

	@Override
	public Person getUserDetails(String userName) {
		
		return null;
	}

	@Override
	public Map<String, String> getStatusTypes() {

		return null;
	}

	@Override
	public BufferedImage generateObfuscatedImage(String text) {

		return null;
	}

}
