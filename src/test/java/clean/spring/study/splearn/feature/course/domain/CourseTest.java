package clean.spring.study.splearn.feature.course.domain;

import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.feature.instructor.domain.InstructorFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CourseTest {

    private Course course;

    @BeforeEach
    void setUp() {
        this.course = CourseFixture.createCourse();
        this.course.updateInfo(new CourseUpdateInfo(course.getTitle(), "description update"));
    }

    @Test
    public void create() {
        Instructor instructor = InstructorFixture.createActiveInstructor();
        Course course = new Course("clean spring", instructor, "desc");

        assertThat(course.getInstructor()).isEqualTo(instructor);
        assertThat(course.getTitle()).isEqualTo("clean spring");
        assertThat(course.getStatus()).isEqualTo(CourseStatus.DRAFT);
        assertThat(course.getDetail().getDescription()).isEqualTo("desc");
        assertThat(course.getDetail().getCreatedAt()).isNotNull();
    }

    @Test
    public void createFailNotActiveInstructor() {
        Instructor instructor = InstructorFixture.createInstructor();

        Assertions.assertThatThrownBy(() -> new Course("clean spring", instructor, "desc"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void submitForReview() {

        course.submitForReview();

        assertThat(course.getStatus()).isEqualTo(CourseStatus.IN_REVIEW);

        assertThatThrownBy(() -> course.submitForReview())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void submitForReviewFail() {

        Instructor instructor = InstructorFixture.createActiveInstructor();
        Course course = new Course("test", instructor, null);

        assertThatThrownBy(() -> course.submitForReview())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void publish() {

        course.submitForReview();

        course.publish();

        assertThat(course.getStatus()).isEqualTo(CourseStatus.PUBLISHED);
        assertThat(course.getDetail().getPublishedAt()).isNotNull();

    }

    @Test
    void archive() {

        course.submitForReview();

        course.publish();

        course.archive();

        assertThat(course.getStatus()).isEqualTo(CourseStatus.ARCHIVED);
        assertThat(course.getDetail().getArchivedAt()).isNotNull();
        assertThatThrownBy(() -> course.archive())
                .isInstanceOf(IllegalStateException.class);

    }

    @Test
    void updateInfo() {

        String titleTest = "title test";
        String updateDesc = "update desc";

        course.updateInfo(new CourseUpdateInfo(titleTest, updateDesc));

        assertThat(course.getTitle()).isEqualTo(titleTest);
        assertThat(course.getDetail().getDescription()).isEqualTo(updateDesc);
    }
}