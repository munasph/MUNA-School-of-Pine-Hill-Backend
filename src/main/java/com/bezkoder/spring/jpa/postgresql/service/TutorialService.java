package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;
import java.util.Optional;

import com.bezkoder.spring.jpa.postgresql.entity.Tutorial;

public interface TutorialService {

	List<Tutorial> findAll(String title);

	Optional<Tutorial> findById(long id);

	List<Tutorial> findPublished();

	Tutorial create(Tutorial tutorial);

	Tutorial update(long id, Tutorial tutorial);

	void deleteById(long id);

	void deleteAll();
}
