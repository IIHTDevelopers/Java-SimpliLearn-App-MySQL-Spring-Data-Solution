package com.simplilearn.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplilearn.dto.CourseDTO;
import com.simplilearn.dto.StudentDTO;
import com.simplilearn.entity.Course;
import com.simplilearn.entity.Student;
import com.simplilearn.exception.ResourceNotFoundException;
import com.simplilearn.repo.CourseRepository;
import com.simplilearn.repo.StudentRepository;
import com.simplilearn.service.StudentService;

import jakarta.transaction.Transactional;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository,
			ModelMapper modelMapper) {
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<CourseDTO> viewStudentEnrollments(Long studentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found"));

		return student.getCourses().stream().map(course -> modelMapper.map(course, CourseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public StudentDTO manageStudentEnrollment(Long studentId, Long courseId, boolean enroll) {
		if (!studentRepository.existsById(studentId)) {
			throw new ResourceNotFoundException("Student not found");
		}

		if (!courseRepository.existsById(courseId)) {
			throw new ResourceNotFoundException("Course not found");
		}

		if (enroll) {
			studentRepository.enrollStudentInCourse(studentId, courseId);
		} else {
			studentRepository.unenrollStudentFromCourse(studentId, courseId);
		}

		Student student = studentRepository.findById(studentId).get();
		return modelMapper.map(student, StudentDTO.class);
	}

	@Override
	public List<StudentDTO> listStudentsForCourse(Long courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		return course.getStudents().stream().map(student -> modelMapper.map(student, StudentDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public StudentDTO createStudent(StudentDTO studentDTO) {
		Student student = modelMapper.map(studentDTO, Student.class);
		Student savedStudent = studentRepository.save(student);
		return modelMapper.map(savedStudent, StudentDTO.class);
	}
}
