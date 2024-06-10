package com.simplilearn.functional;

import static com.simplilearn.utils.MasterData.asJsonString;
import static com.simplilearn.utils.MasterData.getCourseDTO;
import static com.simplilearn.utils.MasterData.getCourseDTOPage;
import static com.simplilearn.utils.TestUtils.businessTestFile;
import static com.simplilearn.utils.TestUtils.currentTest;
import static com.simplilearn.utils.TestUtils.testReport;
import static com.simplilearn.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.simplilearn.controller.CourseController;
import com.simplilearn.dto.CourseDTO;
import com.simplilearn.service.CourseService;

@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc
public class CourseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService courseService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateCourse() throws Exception {
		CourseDTO courseDTO = getCourseDTO();
		when(courseService.createCourse(any(CourseDTO.class))).thenReturn(courseDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/courses").content(asJsonString(courseDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == HttpStatus.CREATED.value() ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testUpdateCourse() throws Exception {
		CourseDTO courseDTO = getCourseDTO();
		when(courseService.updateCourse(anyLong(), any(CourseDTO.class))).thenReturn(courseDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/courses/" + courseDTO.getId())
				.content(asJsonString(courseDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(courseDTO)) ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testGetCourseById() throws Exception {
		CourseDTO courseDTO = getCourseDTO();
		when(courseService.getCourse(anyLong())).thenReturn(courseDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/courses/" + courseDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(courseDTO)) ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testDeleteCourseById() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/courses/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getStatus() == HttpStatus.NO_CONTENT.value() ? "true" : "false", businessTestFile);
	}

	@Test
	public void testListAllCourses() throws Exception {
		Page<CourseDTO> courseDTOS = getCourseDTOPage(0, 10);
		when(courseService.listAllCourses(any(Pageable.class))).thenReturn(courseDTOS);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/courses")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), !result.getResponse().getContentAsString().isEmpty() ? "true" : "false",
				businessTestFile);
	}
}
