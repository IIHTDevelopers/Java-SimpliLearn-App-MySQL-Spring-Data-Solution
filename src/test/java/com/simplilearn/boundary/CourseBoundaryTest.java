package com.simplilearn.boundary;

import static com.simplilearn.utils.TestUtils.boundaryTestFile;
import static com.simplilearn.utils.TestUtils.currentTest;
import static com.simplilearn.utils.TestUtils.testReport;
import static com.simplilearn.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.simplilearn.dto.CourseDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class CourseBoundaryTest {

	private static Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@After
	public void afterAll() {
		testReport();
	}

	@Test
	public void testTitleNotBlank() throws IOException {
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setTitle("");
		Set<ConstraintViolation<CourseDTO>> violations = validator.validate(courseDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testTitleSize() throws IOException {
		CourseDTO courseDTO = new CourseDTO();
		// Generating a string longer than 100 characters
		courseDTO.setTitle("a".repeat(101));
		Set<ConstraintViolation<CourseDTO>> violations = validator.validate(courseDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testDescriptionSize() throws IOException {
		CourseDTO courseDTO = new CourseDTO();
		// Generating a string longer than 1000 characters
		courseDTO.setDescription("a".repeat(1001));
		Set<ConstraintViolation<CourseDTO>> violations = validator.validate(courseDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}
