package clean.spring.study.splearn.feature.course.application;


import clean.spring.study.splearn.feature.course.application.provided.CourseFinder;
import clean.spring.study.splearn.feature.course.application.required.CourseRepository;
import clean.spring.study.splearn.feature.course.domain.Course;
import clean.spring.study.splearn.support.stereotype.ApplicationService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationService
@RequiredArgsConstructor
public class CourseQueryService implements CourseFinder {

    private final CourseRepository courseRepository;

    @Override
    public Course find(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(
                () -> new IllegalArgumentException("강의를 찾을 수 없습니다." + courseId)
        );
    }

    @Override
    public List<Course> findByTitle(String keyword) {
        return courseRepository.findByTitleContaining(keyword);
    }

    @Override
    public List<Course> findByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }

}
