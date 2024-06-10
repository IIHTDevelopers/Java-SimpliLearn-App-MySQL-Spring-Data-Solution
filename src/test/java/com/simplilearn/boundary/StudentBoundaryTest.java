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

import com.simplilearn.dto.StudentDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class StudentBoundaryTest {

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
	public void testNameNotBlank() throws IOException {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setName("");
		Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testNameSize() throws IOException {
		StudentDTO studentDTO = new StudentDTO();
		// Name too short
		studentDTO.setName("A");
		Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			// Testing for a name that is too short
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}

		// Resetting the name to be too long
		studentDTO.setName("a".repeat(101));
		violations = validator.validate(studentDTO);
		try {
			// Testing for a name that is too long
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testEmailNotBlank() throws IOException {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setEmail("");
		Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testEmailValid() throws IOException {
		StudentDTO studentDTO = new StudentDTO();
		// Invalid email format
		studentDTO.setEmail("invalid-email");
		Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}
