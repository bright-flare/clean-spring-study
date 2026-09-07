package clean.spring.study.splearn.feature.course.adapter.persistence;

import clean.spring.study.splearn.feature.course.application.required.CourseRepository;
import clean.spring.study.splearn.feature.course.domain.Course;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryAdapter implements CourseRepository {

    private final CourseJpaRepository courseJpaRepository;

    @Override
    public Course save(Course course) {
        return courseJpaRepository.save(course);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseJpaRepository.findById(id);
    }

    @Override
    public List<Course> findByTitleContaining(String keyword) {
        return courseJpaRepository.findByTitleContaining(keyword);
    }

    @Override
    public List<Course> findByInstructorId(Long instructorId) {
        return courseJpaRepository.findByInstructorId(instructorId);
    }

    @Override
    public Optional<Course> findByInstructorAndTitle(Instructor instructor, String title) {
        return courseJpaRepository.findByInstructorAndTitle(instructor, title);
    }

}
