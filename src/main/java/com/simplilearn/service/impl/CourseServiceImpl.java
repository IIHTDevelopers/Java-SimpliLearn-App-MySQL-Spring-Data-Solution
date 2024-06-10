package com.simplilearn.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.simplilearn.dto.CourseDTO;
import com.simplilearn.entity.Course;
import com.simplilearn.exception.ResourceNotFoundException;
import com.simplilearn.repo.CourseRepository;
import com.simplilearn.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
		this.courseRepository = courseRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CourseDTO createCourse(CourseDTO courseDTO) {
		Course course = modelMapper.map(courseDTO, Course.class);
		Course savedCourse = courseRepository.save(course);
		return modelMapper.map(savedCourse, CourseDTO.class);
	}

	@Override
	public CourseDTO updateCourse(Long courseId, CourseDTO courseDTO) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		course.setTitle(courseDTO.getTitle());
		course.setDescription(courseDTO.getDescription());
		Course updatedCourse = courseRepository.save(course);
		return modelMapper.map(updatedCourse, CourseDTO.class);
	}

	@Override
	public boolean deleteCourse(Long courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		courseRepository.delete(course);
		return true;
	}

	@Override
	public CourseDTO getCourse(Long courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found"));
		return modelMapper.map(course, CourseDTO.class);
	}

	@Override
	public Page<CourseDTO> listAllCourses(Pageable pageable) {
		Page<Course> courses = courseRepository.findAllByOrderByTitleAsc(pageable);
		Page<CourseDTO> dtoPage = courses.map(course -> modelMapper.map(course, CourseDTO.class));
		return dtoPage;
	}

	@Override
	public List<CourseDTO> searchCourses(String title) {
		List<Course> courses = courseRepository.findByTitleOrderByTitleAsc(title);
		List<CourseDTO> courseDTOs = courses.stream().map(course -> modelMapper.map(course, CourseDTO.class))
				.collect(Collectors.toList());
		return courseDTOs;
	}
}
