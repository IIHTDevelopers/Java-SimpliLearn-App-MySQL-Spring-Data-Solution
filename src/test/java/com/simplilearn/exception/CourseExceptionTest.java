package com.simplilearn.exception;

import static com.simplilearn.utils.TestUtils.currentTest;
import static com.simplilearn.utils.TestUtils.exceptionTestFile;
import static com.simplilearn.utils.TestUtils.testReport;
import static com.simplilearn.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

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

import com.simplilearn.controller.CourseController;
import com.simplilearn.dto.CourseDTO;
import com.simplilearn.service.CourseService;
import com.simplilearn.utils.MasterData;

@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc
public class CourseExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService courseService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateCourseInvalidDataException() throws Exception {
		CourseDTO courseDTO = MasterData.getCourseDTO();
		courseDTO.setTitle("");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/courses")
				.content(MasterData.asJsonString(courseDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testUpdateCourseInvalidDataException() throws Exception {
		CourseDTO courseDTO = MasterData.getCourseDTO();
		courseDTO.setTitle("");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/courses/" + courseDTO.getId())
				.content(MasterData.asJsonString(courseDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testGetCourseByIdResourceNotFoundException() throws Exception {
		CourseDTO courseDTO = MasterData.getCourseDTO();
		courseDTO.setId(100L);
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Course not found");

		when(this.courseService.getCourse(courseDTO.getId()))
				.thenThrow(new ResourceNotFoundException("Course not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/courses/" + courseDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testDeleteCourseByIdResourceNotFoundException() throws Exception {
		CourseDTO courseDTO = MasterData.getCourseDTO();
		courseDTO.setId(100L);
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Course not found");

		when(this.courseService.deleteCourse(courseDTO.getId()))
				.thenThrow(new ResourceNotFoundException("Course not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/courses/" + courseDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}
}
