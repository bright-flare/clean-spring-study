package clean.spring.study.splearn.feature.course.application.provided;

import clean.spring.study.splearn.feature.course.domain.CourseStatus;
import clean.spring.study.splearn.support.stereotype.ApplicationServiceTest;
import clean.spring.study.splearn.support.test.BaseApplicationServiceTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationServiceTest
@RequiredArgsConstructor
class CoursePublisherTest extends BaseApplicationServiceTest {

    private final CoursePublisher coursePublisher;
    private final CourseCreator courseCreator;

    @BeforeEach
    void setUp() {
        prepareInstructor();
        prepareCourse();
    }

    @Test
    void submitForReview() {

        var reviewed = coursePublisher.submitForReview(course.getId());

        assertThat(reviewed.getStatus()).isEqualTo(CourseStatus.IN_REVIEW);
    }

    @Test
    void publish() {

        coursePublisher.submitForReview(course.getId());

        var published = coursePublisher.publish(course.getId());

        assertThat(published.getStatus()).isEqualTo(CourseStatus.PUBLISHED);
    }

    @Test
    void archive() {

        coursePublisher.submitForReview(course.getId());
        coursePublisher.publish(course.getId());

        var archived = coursePublisher.archive(course.getId());

        assertThat(archived.getStatus()).isEqualTo(CourseStatus.ARCHIVED);
    }
}