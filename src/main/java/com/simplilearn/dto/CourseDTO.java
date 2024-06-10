package com.simplilearn.dto;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CourseDTO {
	private Long id;

	@NotBlank(message = "Title is required")
	@Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
	private String title;

	@Size(max = 1000, message = "Description cannot exceed 1000 characters")
	private String description;

	private Set<Long> studentIds = new HashSet<>();

	public CourseDTO() {
		super();
	}

	public CourseDTO(Long id,
			@NotBlank(message = "Title is required") @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters") String title,
			@Size(max = 1000, message = "Description cannot exceed 1000 characters") String description,
			Set<Long> studentIds) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.studentIds = studentIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Long> getStudentIds() {
		return studentIds;
	}

	public void setStudentIds(Set<Long> studentIds) {
		this.studentIds = studentIds;
	}

	@Override
	public String toString() {
		return "CourseDTO [id=" + id + ", title=" + title + ", description=" + description + ", studentIds="
				+ studentIds + "]";
	}
}
