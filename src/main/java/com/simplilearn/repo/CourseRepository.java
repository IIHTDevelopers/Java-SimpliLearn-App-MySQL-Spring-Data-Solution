package com.simplilearn.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simplilearn.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	/**
	 * Finds courses by their title containing the specified string and ordered by
	 * title in ascending order.
	 * 
	 * @param title the title or part of the title to search for
	 * @return a list of courses matching the search criteria
	 */
	List<Course> findByTitleOrderByTitleAsc(String title);

	/**
	 * Lists all courses ordered by their title.
	 * 
	 * @param pageable data
	 * @return a Page of courses
	 */
	Page<Course> findAllByOrderByTitleAsc(Pageable pageable);
}
