package clean.spring.study.splearn.feature.course.application.provided;

import clean.spring.study.splearn.feature.course.application.provided.dto.CourseCreateRequest;
import clean.spring.study.splearn.feature.course.application.provided.dto.CourseInfoUpdateRequest;
import clean.spring.study.splearn.feature.course.domain.Course;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.support.exception.ValidationException;

public interface CourseValidator {
    void validateForCreate(Instructor instructor, CourseCreateRequest request) throws ValidationException;

    void validateForUpdate(Course course, CourseInfoUpdateRequest request) throws ValidationException;
}
