package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.model.Tutorial;
import com.bezkoder.spring.jpa.postgresql.repository.TutorialRepository;
import com.bezkoder.spring.jpa.postgresql.service.TutorialService;

@Service
@Transactional(readOnly = true)
public class TutorialServiceImpl implements TutorialService {

	private final TutorialRepository tutorialRepository;

	public TutorialServiceImpl(TutorialRepository tutorialRepository) {
		this.tutorialRepository = tutorialRepository;
	}

	@Override
	public List<Tutorial> findAll(String title) {
		List<Tutorial> tutorials = new ArrayList<>();
		if (title == null || title.isBlank()) {
			tutorialRepository.findAll().forEach(tutorials::add);
		} else {
			tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
		}
		return tutorials;
	}

	@Override
	public Optional<Tutorial> findById(long id) {
		return tutorialRepository.findById(id);
	}

	@Override
	public List<Tutorial> findPublished() {
		return tutorialRepository.findByPublished(true);
	}

	@Override
	@Transactional
	public Tutorial create(Tutorial tutorial) {
		Tutorial entity = new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false);
		return tutorialRepository.save(entity);
	}

	@Override
	@Transactional
	public Tutorial update(long id, Tutorial tutorial) {
		Tutorial existing = tutorialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tutorial not found with id: " + id));
		existing.setTitle(tutorial.getTitle());
		existing.setDescription(tutorial.getDescription());
		existing.setPublished(tutorial.isPublished());
		return tutorialRepository.save(existing);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		if (!tutorialRepository.existsById(id)) {
			throw new ResourceNotFoundException("Tutorial not found with id: " + id);
		}
		tutorialRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void deleteAll() {
		tutorialRepository.deleteAll();
	}
}
