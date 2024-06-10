package com.simplilearn.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.simplilearn.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	Optional<Student> findByEmail(String email);

	/**
	 * Enrolls a student in a specified course. This method directly modifies the
	 * join table representing the many-to-many relationship between students and
	 * courses. If the student is already enrolled in the course, this operation
	 * does not insert a duplicate entry.
	 *
	 * @param studentId the ID of the student to be enrolled in the course
	 * @param courseId  the ID of the course in which the student will be enrolled
	 */
	@Modifying
	@Query(value = "INSERT INTO student_course (student_id, course_id) VALUES (:studentId, :courseId) ON DUPLICATE KEY UPDATE student_id=student_id", nativeQuery = true)
	void enrollStudentInCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

	/**
	 * Unenrolls a student from a specified course. This method directly modifies
	 * the join table representing the many-to-many relationship between students
	 * and courses by removing the entry that links the student to the course.
	 *
	 * @param studentId the ID of the student to be unenrolled from the course
	 * @param courseId  the ID of the course from which the student will be
	 *                  unenrolled
	 */
	@Modifying
	@Query(value = "DELETE FROM student_course WHERE student_id = :studentId AND course_id = :courseId", nativeQuery = true)
	void unenrollStudentFromCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

}
