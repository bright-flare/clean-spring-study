package clean.spring.study.splearn.feature.course.application;

import clean.spring.study.splearn.feature.course.application.provided.CourseValidator;
import clean.spring.study.splearn.feature.course.application.provided.dto.CourseCreateRequest;
import clean.spring.study.splearn.feature.course.application.provided.dto.CourseInfoUpdateRequest;
import clean.spring.study.splearn.feature.course.application.required.CourseRepository;
import clean.spring.study.splearn.feature.course.domain.Course;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.support.exception.ValidationException;
import clean.spring.study.splearn.support.stereotype.ApplicationService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApplicationService
@RequiredArgsConstructor
public class CourseValidationService implements CourseValidator {

    private final CourseRepository courseRepository;

    @Override
    public void validateForCreate(Instructor instructor, CourseCreateRequest request) throws ValidationException {

        instructor.ensureActive();

        List<String> errors = new ArrayList<>();

        checkTitleDuplicationForCreate(instructor, request.title(), errors);
        checkBannedWords(request.title(), errors);
        checkBannedWords(request.description(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

    }

    @Override
    public void validateForUpdate(Course course, CourseInfoUpdateRequest request) throws ValidationException {
        course.getInstructor().ensureActive();

        List<String> errors = new ArrayList<>();

        checkTitleDuplicationForUpdate(course, request.title(), errors);
        checkBannedWords(request.title(), errors);
        checkBannedWords(request.description(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void checkBannedWords(String text, List<String> errors) {
        // todo
    }

    private void checkTitleDuplicationForCreate(Instructor instructor, String title, List<String> errors) {

        if (courseRepository.findByInstructorAndTitle(instructor, title).isPresent()) {
            errors.add("이미 사용중인 강의 제목입니다." + title);
        }

    }

    private void checkTitleDuplicationForUpdate(Course course, String title, List<String> errors) {

        courseRepository.findByInstructorAndTitle(course.getInstructor(), title).ifPresent(found -> {
            if (!found.equals(course)) {
                errors.add("이미 사용중인 강의 제목입니다." + title);
            }
        });

    }

}
