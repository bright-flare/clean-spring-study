package clean.spring.study.splearn.feature.course.domain;

import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.feature.instructor.domain.InstructorFixture;
import org.instancio.Instancio;

public class CourseFixture {

    public static Course createCourse() {

        Instructor instructor = InstructorFixture.createActiveInstructor();

        String title = Instancio.gen().string().minLength(2).maxLength(100).get();
        String description = Instancio.gen().string().maxLength(500).nullable().get();

        return new Course(title, instructor, description);
    }
}
