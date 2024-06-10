package com.simplilearn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simplilearn.dto.CourseDTO;
import com.simplilearn.service.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

	private final CourseService courseService;

	@Autowired
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@PostMapping
	public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
		CourseDTO createdCourse = courseService.createCourse(courseDTO);
		return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
	}

	@PutMapping("/{courseId}")
	public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long courseId,
			@Valid @RequestBody CourseDTO courseDTO) {
		CourseDTO updatedCourse = courseService.updateCourse(courseId, courseDTO);
		return ResponseEntity.ok(updatedCourse);
	}

	@GetMapping("/{courseId}")
	public ResponseEntity<CourseDTO> getCourse(@PathVariable Long courseId) {
		CourseDTO course = courseService.getCourse(courseId);
		return ResponseEntity.ok(course);
	}

	@DeleteMapping("/{courseId}")
	public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
		courseService.deleteCourse(courseId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<Page<CourseDTO>> listAllCourses(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<CourseDTO> courses = courseService.listAllCourses(pageable);
		return ResponseEntity.ok(courses);
	}
}
