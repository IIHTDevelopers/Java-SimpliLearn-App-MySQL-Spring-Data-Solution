package com.simplilearn.functional;

import static com.simplilearn.utils.MasterData.asJsonString;
import static com.simplilearn.utils.MasterData.getStudentDTO;
import static com.simplilearn.utils.TestUtils.businessTestFile;
import static com.simplilearn.utils.TestUtils.currentTest;
import static com.simplilearn.utils.TestUtils.testReport;
import static com.simplilearn.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.simplilearn.controller.StudentController;
import com.simplilearn.dto.CourseDTO;
import com.simplilearn.dto.StudentDTO;
import com.simplilearn.service.StudentService;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc
public class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService studentService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateStudent() throws Exception {
		StudentDTO studentDTO = getStudentDTO();
		when(studentService.createStudent(any(StudentDTO.class))).thenReturn(studentDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/students").content(asJsonString(studentDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == HttpStatus.CREATED.value() ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testEnrollStudentInCourse() throws Exception {
		StudentDTO enrolledStudent = getStudentDTO();
		Long studentId = enrolledStudent.getId();
		Long courseId = 1L; // Assuming courseId to use for the test
		when(studentService.manageStudentEnrollment(anyLong(), anyLong(), anyBoolean())).thenReturn(enrolledStudent);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/students/enroll")
				.param("studentId", String.valueOf(studentId)).param("courseId", String.valueOf(courseId))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == HttpStatus.CREATED.value() ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testViewStudentEnrollments() throws Exception {
		List<CourseDTO> enrolledCourses = Arrays.asList(new CourseDTO());
		Long studentId = 1L; // Assuming studentId to use for the test
		when(studentService.viewStudentEnrollments(anyLong())).thenReturn(enrolledCourses);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/students/{studentId}/courses", studentId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), !result.getResponse().getContentAsString().isEmpty() ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testListStudentsForCourse() throws Exception {
		List<StudentDTO> students = Arrays.asList(getStudentDTO());
		Long courseId = 1L; // Assuming courseId to use for the test
		when(studentService.listStudentsForCourse(anyLong())).thenReturn(students);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/students/courses/{courseId}", courseId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), !result.getResponse().getContentAsString().isEmpty() ? "true" : "false",
				businessTestFile);
	}
}
