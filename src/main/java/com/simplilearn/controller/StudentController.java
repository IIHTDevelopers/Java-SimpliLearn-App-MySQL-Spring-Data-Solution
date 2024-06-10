package com.simplilearn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simplilearn.dto.CourseDTO;
import com.simplilearn.dto.StudentDTO;
import com.simplilearn.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	private final StudentService studentService;

	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@PostMapping
	public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
		StudentDTO createdStudent = studentService.createStudent(studentDTO);
		return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
	}

	@PostMapping("/enroll")
	public ResponseEntity<StudentDTO> enrollStudentInCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
		StudentDTO enrolledStudent = studentService.manageStudentEnrollment(studentId, courseId, true);
		return new ResponseEntity<>(enrolledStudent, HttpStatus.CREATED);
	}

	@GetMapping("/{studentId}/courses")
	public ResponseEntity<List<CourseDTO>> viewStudentEnrollments(@PathVariable Long studentId) {
		List<CourseDTO> enrolledCourses = studentService.viewStudentEnrollments(studentId);
		return ResponseEntity.ok(enrolledCourses);
	}

	@PostMapping("/{studentId}/courses/{courseId}")
	public ResponseEntity<StudentDTO> manageStudentEnrollment(@PathVariable Long studentId, @PathVariable Long courseId,
			@RequestParam boolean enroll) {
		StudentDTO updatedStudent = studentService.manageStudentEnrollment(studentId, courseId, enroll);
		return ResponseEntity.ok(updatedStudent);
	}

	@GetMapping("/courses/{courseId}")
	public ResponseEntity<List<StudentDTO>> listStudentsForCourse(@PathVariable Long courseId) {
		List<StudentDTO> students = studentService.listStudentsForCourse(courseId);
		return ResponseEntity.ok(students);
	}
}
