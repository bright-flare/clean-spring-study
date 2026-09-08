package clean.spring.study.splearn.feature.course.domain;

import clean.spring.study.splearn.feature.course.application.provided.dto.CourseCreateRequest;
import clean.spring.study.splearn.feature.course.application.provided.dto.CourseInfoUpdateRequest;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.feature.instructor.domain.InstructorFixture;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.instancio.Instancio;
import org.instancio.Select;

import java.util.Optional;

import static org.instancio.Instancio.gen;
import static org.instancio.Select.field;

public class CourseFixture {

    public static Course createCourse(@Nullable Instructor instructorParam, @Nullable String titleParam) {

        String title = Optional.ofNullable(titleParam)
                .orElseGet(() -> gen().string().minLength(2).maxLength(100).get());

        Instructor instructor = Optional.ofNullable(instructorParam)
                .orElseGet(InstructorFixture::createActiveInstructor);

        String description = gen().string().maxLength(500).nullable().get();

        return new Course(title, instructor, description);
    }

    public static Course createCourse() {
        return createCourse(null, null);
    }

    public static Course createCourse(Instructor instructor) {
        return createCourse(instructor, null);
    }

    public static CourseCreateRequest createCourseCreateRequest(Long instructorId, @Nullable String _title) {

        String title = Optional.ofNullable(_title)
                .orElseGet(() -> gen().string().minLength(2).maxLength(100).get());

        return Instancio.of(CourseCreateRequest.class)
                .set(field(CourseCreateRequest::instructorId), instructorId)
                .set(field(CourseCreateRequest::title), title)
                .generate(field(CourseCreateRequest::description), gen -> gen.string().maxLength(500).nullable())
                .create();
    }

    public static CourseInfoUpdateRequest createCourseUpdateRequest(@Nullable String _title) {
        String title = Optional.ofNullable(_title)
                .orElseGet(() -> gen().string().minLength(2).maxLength(100).get());

        return Instancio.of(CourseInfoUpdateRequest.class)
                .set(field(CourseInfoUpdateRequest::title), title)
                .generate(field(CourseInfoUpdateRequest::description), gen -> gen.string().maxLength(500).nullable())
                .create();
    }

}
