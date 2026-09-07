package clean.spring.study.splearn.feature.course.application;

import clean.spring.study.splearn.feature.course.application.provided.CourseCreator;
import clean.spring.study.splearn.feature.course.application.provided.CourseFinder;
import clean.spring.study.splearn.feature.course.application.provided.dto.CourseCreateRequest;
import clean.spring.study.splearn.feature.course.application.provided.dto.CourseInfoUpdateRequest;
import clean.spring.study.splearn.feature.course.application.required.CourseRepository;
import clean.spring.study.splearn.feature.course.domain.Course;
import clean.spring.study.splearn.feature.instructor.application.provided.InstructorFinder;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.support.stereotype.ValidatedApplicationService;
import lombok.RequiredArgsConstructor;

@ValidatedApplicationService
@RequiredArgsConstructor
public class CourseModifyService implements CourseCreator {

    private final CourseRepository courseRepository;
    private final CourseFinder courseFinder;
    private final InstructorFinder instructorFinder;

    @Override
    public Course create(CourseCreateRequest request) {

        Instructor instructor = instructorFinder.findById(request.instructorId());



        return null;
    }

    @Override
    public Course updateInfo(Long courseId, CourseInfoUpdateRequest request) {
        return null;
    }
}
