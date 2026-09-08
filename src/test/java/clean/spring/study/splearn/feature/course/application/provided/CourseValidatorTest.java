package clean.spring.study.splearn.feature.course.application.provided;

import clean.spring.study.splearn.feature.course.application.provided.dto.CourseCreateRequest;
import clean.spring.study.splearn.feature.course.application.required.CourseRepository;
import clean.spring.study.splearn.feature.course.domain.Course;
import clean.spring.study.splearn.feature.course.domain.CourseFixture;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.support.exception.ValidationException;
import clean.spring.study.splearn.support.stereotype.ApplicationServiceTest;
import clean.spring.study.splearn.support.test.BaseApplicationServiceTest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ApplicationServiceTest
@RequiredArgsConstructor
class CourseValidatorTest extends BaseApplicationServiceTest {

    private final CourseValidator courseValidator;
    private final CourseRepository courseRepository;

    @Test
    void titleDuplication() {
        Instructor instructor1 = prepareInstructor();
        Instructor instructor2 = prepareInstructor();

        Course course1 = courseRepository.save(CourseFixture.createCourse(instructor1, "spring 1"));
        Course course2 = courseRepository.save(CourseFixture.createCourse(instructor2, "spring 2"));

        // 중복되지 않는 제목 -> OK
        courseValidator.validateForCreate(instructor1, new CourseCreateRequest(instructor1.getId(), "spring test", null));

        // 중복 제목 -> Fail
        assertThatThrownBy(() -> courseValidator.validateForCreate(instructor1, new CourseCreateRequest(instructor1.getId(), "spring 1", null)))
                .isInstanceOfSatisfying(ValidationException.class, e -> assertThat(e.getErrors()).hasSize(1));

        // instructor2에서 instructor1이 등록한 제목 체크 -> OK
        courseValidator.validateForCreate(instructor2, new CourseCreateRequest(instructor2.getId(), "spring 1", null));

    }

    @Test
    void titleDuplicationForUpdate() {
        Instructor instructor1 = prepareInstructor();
        Instructor instructor2 = prepareInstructor();

        Course course1 = courseRepository.save(CourseFixture.createCourse(instructor1, "spring 1"));
        Course course1_1 = courseRepository.save(CourseFixture.createCourse(instructor1, "spring 테스트"));

        Course course2 = courseRepository.save(CourseFixture.createCourse(instructor2, "spring 2"));

        // title 변경 없이 update -> OK
        courseValidator.validateForUpdate(course1_1, CourseFixture.createCourseUpdateRequest(course1_1.getTitle()));

        // title 변경하는데 중복 -> OK
        Assertions.assertThatThrownBy(() -> courseValidator.validateForUpdate(course1, CourseFixture.createCourseUpdateRequest(course1_1.getTitle())))
                .isInstanceOfSatisfying(
                        ValidationException.class,
                        e -> assertThat(e.getErrors()).hasSize(1)
                );

    }

}