package com.simplilearn.exception;

import static com.simplilearn.utils.TestUtils.currentTest;
import static com.simplilearn.utils.TestUtils.exceptionTestFile;
import static com.simplilearn.utils.TestUtils.testReport;
import static com.simplilearn.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.simplilearn.controller.StudentController;
import com.simplilearn.service.StudentService;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc
public class StudentExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService studentService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testEnrollStudentInCourseResourceNotFoundException() throws Exception {
		Long studentId = 100L;
		Long courseId = 200L;
		when(studentService.manageStudentEnrollment(studentId, courseId, true))
				.thenThrow(new ResourceNotFoundException("Student or Course not found"));

		MvcResult result = mockMvc.perform(post("/students/{studentId}/courses/{courseId}/enroll", studentId, courseId)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.NOT_FOUND.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testViewStudentEnrollmentsResourceNotFoundException() throws Exception {
		Long studentId = 100L;
		when(studentService.viewStudentEnrollments(studentId))
				.thenThrow(new ResourceNotFoundException("Student not found"));

		MvcResult result = mockMvc
				.perform(get("/students/{studentId}/courses", studentId).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.NOT_FOUND.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testListStudentsForCourseResourceNotFoundException() throws Exception {
		Long courseId = 200L;
		when(studentService.listStudentsForCourse(courseId))
				.thenThrow(new ResourceNotFoundException("Course not found"));

		MvcResult result = mockMvc
				.perform(get("/courses/{courseId}/students", courseId).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.NOT_FOUND.value() ? "true" : "false"),
				exceptionTestFile);
	}
}
