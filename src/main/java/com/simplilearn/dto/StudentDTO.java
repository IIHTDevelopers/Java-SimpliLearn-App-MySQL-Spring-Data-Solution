package com.simplilearn.dto;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StudentDTO {
	private Long id;

	@NotBlank(message = "Name is required")
	@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Email must be a valid email address")
	private String email;

	private Set<Long> courseIds = new HashSet<>();

	public StudentDTO() {
		super();
	}

	public StudentDTO(Long id,
			@NotBlank(message = "Name is required") @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String name,
			@NotBlank(message = "Email is required") @Email(message = "Email must be a valid email address") String email,
			Set<Long> courseIds) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.courseIds = courseIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Long> getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(Set<Long> courseIds) {
		this.courseIds = courseIds;
	}

	@Override
	public String toString() {
		return "StudentDTO [id=" + id + ", name=" + name + ", email=" + email + ", courseIds=" + courseIds + "]";
	}
}
