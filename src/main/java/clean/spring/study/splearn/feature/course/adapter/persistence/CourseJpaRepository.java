package clean.spring.study.splearn.feature.course.adapter.persistence;

import clean.spring.study.splearn.feature.course.domain.Course;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseJpaRepository extends JpaRepository<Course, Long> {

    List<Course> findByTitleContaining(String keyword);

    List<Course> findByInstructorId(Long instructorId);

}
