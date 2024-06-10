package com.simplilearn.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.simplilearn.dto.CourseDTO;

public interface CourseService {

	/**
	 * Creates a new course with the given details.
	 * 
	 * @param courseDTO the course details to be added
	 * @return the created CourseDTO with assigned ID
	 */
	CourseDTO createCourse(CourseDTO courseDTO);

	/**
	 * Updates an existing course with new details.
	 * 
	 * @param courseId  the ID of the course to update
	 * @param courseDTO the new course details
	 * @return the updated CourseDTO
	 */
	CourseDTO updateCourse(Long courseId, CourseDTO courseDTO);

	/**
	 * Gets an existing course with details.
	 * 
	 * @param courseId the ID of the course to get
	 * @return the CourseDTO
	 */
	CourseDTO getCourse(Long courseId);

	/**
	 * Deletes a course by its ID.
	 * 
	 * @param courseId the ID of the course to delete
	 * @return the boolean value representing the status
	 */
	boolean deleteCourse(Long courseId);

	/**
	 * Retrieves a list of all courses in Page.
	 * 
	 * @return a list of CourseDTOs representing all courses
	 */
	Page<CourseDTO> listAllCourses(Pageable pageable);

	/**
	 * Retrieves a list of all courses in list on title basis.
	 * 
	 * @return a list of CourseDTOs representing all courses
	 */
	List<CourseDTO> searchCourses(String text);
}