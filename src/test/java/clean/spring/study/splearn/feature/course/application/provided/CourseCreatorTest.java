package clean.spring.study.splearn.feature.course.application.provided;

import clean.spring.study.splearn.feature.course.domain.Course;
import clean.spring.study.splearn.feature.course.domain.CourseFixture;
import clean.spring.study.splearn.support.stereotype.ApplicationServiceTest;
import clean.spring.study.splearn.support.test.BaseApplicationServiceTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationServiceTest
@RequiredArgsConstructor
class CourseCreatorTest extends BaseApplicationServiceTest {

    private final CourseCreator courseCreator;

    @Test
    void create() {

        prepareInstructor();
        Course course = courseCreator.create(CourseFixture.createCourseCreateRequest(instructor.getId(), null));

        assertThat(course.getId()).isNotNull();

    }

    @Test
    void updateInfo() {

        prepareInstructor();
        Course course = courseCreator.create(CourseFixture.createCourseCreateRequest(instructor.getId(), null));

        courseCreator.updateInfo(course.getId(), CourseFixture.createCourseUpdateRequest("update"));

        assertThat(course.getTitle()).isEqualTo("update");

    }
}