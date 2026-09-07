package clean.spring.study.splearn.feature.course.application.required;

import clean.spring.study.splearn.feature.course.domain.Course;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    Course save(Course course);

    Optional<Course> findById(Long id);

    List<Course> findByTitleContaining(String keyword);

    default List<Course> findByInstructor(Instructor instructor) {
        return findByInstructorId(instructor.getId());
    }

    List<Course> findByInstructorId(Long instructorId);

}
