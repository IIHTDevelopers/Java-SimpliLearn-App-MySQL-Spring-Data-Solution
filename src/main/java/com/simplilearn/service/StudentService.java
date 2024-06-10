package com.simplilearn.service;

import java.util.List;

import com.simplilearn.dto.CourseDTO;
import com.simplilearn.dto.StudentDTO;

public interface StudentService {

	/**
	 * Creates a new student with the given details.
	 * 
	 * @param studentDTO the student details to be added
	 * @return the created StudentDTO with assigned ID
	 */
	StudentDTO createStudent(StudentDTO studentDTO);

	/**
	 * Views a list of courses a specific student is enrolled in.
	 * 
	 * @param studentId the ID of the student
	 * @return a list of CourseDTOs the student is enrolled in
	 */
	List<CourseDTO> viewStudentEnrollments(Long studentId);

	/**
	 * Adds or removes a student's enrollment in a course.
	 * 
	 * @param studentId the ID of the student
	 * @param courseId  the ID of the course to enroll in or unenroll from
	 * @param enroll    true to enroll the student, false to unenroll
	 * @return the updated StudentDTO after enrollment changes
	 */
	StudentDTO manageStudentEnrollment(Long studentId, Long courseId, boolean enroll);

	/**
	 * Lists all students enrolled in a specific course.
	 * 
	 * @param courseId the ID of the course
	 * @return a list of StudentDTOs enrolled in the course
	 */
	List<StudentDTO> listStudentsForCourse(Long courseId);
}
