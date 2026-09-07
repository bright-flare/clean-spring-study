package clean.spring.study.splearn.feature.course.domain;

import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.feature.instructor.domain.InstructorFixture;
import jakarta.annotation.Nullable;
import org.instancio.Instancio;

import java.util.Optional;

import static org.instancio.Instancio.gen;

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

}
