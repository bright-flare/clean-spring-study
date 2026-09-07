package clean.spring.study.splearn.feature.course.application.provided;

import clean.spring.study.splearn.feature.course.application.provided.dto.CourseCreateRequest;
import clean.spring.study.splearn.feature.course.application.provided.dto.CourseInfoUpdateRequest;
import clean.spring.study.splearn.feature.course.domain.Course;
import jakarta.validation.Valid;

/**
 * 강의를 준비하는 작업
 */
public interface CourseCreator {
    Course create(@Valid CourseCreateRequest request);

    Course updateInfo(Long courseId, @Valid CourseInfoUpdateRequest request);


}
