package clean.spring.study.splearn.feature.course.application.required;

import clean.spring.study.splearn.feature.course.adapter.persistence.CourseRepositoryAdapter;
import clean.spring.study.splearn.feature.course.domain.Course;
import clean.spring.study.splearn.feature.course.domain.CourseFixture;
import clean.spring.study.splearn.feature.instructor.adapter.persistence.InstructorRepositoryAdapter;
import clean.spring.study.splearn.feature.instructor.application.required.InstructorRepository;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.feature.instructor.domain.InstructorFixture;
import clean.spring.study.splearn.feature.member.adapter.persistence.MemberRepositoryAdapter;
import clean.spring.study.splearn.feature.member.application.required.MemberRepository;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.MemberFixture;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@RequiredArgsConstructor
@Import({
        CourseRepositoryAdapter.class,
        MemberRepositoryAdapter.class,
        InstructorRepositoryAdapter.class
})
class CourseRepositoryTest {

    final CourseRepository courseRepository;
    final EntityManager entityManager;
    final MemberRepository memberRepository;
    final InstructorRepository instructorRepository;

    Member member;
    Instructor instructor;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(MemberFixture.createActiveMember());
        instructor = instructorRepository.save(InstructorFixture.createActiveInstructor(member));
    }

    @Test
    void save() {

        Course course = CourseFixture.createCourse(instructor);
        course = courseRepository.save(course);

        assertThat(course.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        Course found = courseRepository.findById(course.getId()).orElseThrow();
        assertThat(found.getId()).isEqualTo(course.getId());

    }

    @Test
    void findById() {
    }

    @Test
    void findByTitleContaining() {
        List<Long> ids = Stream.of(
                        CourseFixture.createCourse(instructor, "hello title 1"),
                        CourseFixture.createCourse(instructor, "hello title 2"),
                        CourseFixture.createCourse(instructor, "hello title 3")
                )
                .map(course -> courseRepository.save(course).getId())
                .toList();

        assertThat(courseRepository.findByTitleContaining("1").stream().map(Course::getId))
                .isEqualTo(List.of(ids.getFirst()));

        assertThat(courseRepository.findByTitleContaining("hello").stream().map(Course::getId))
                .isEqualTo(List.of(ids.get(0), ids.get(1), ids.get(2)));

        assertThat(courseRepository.findByTitleContaining("none").stream().map(Course::getId))
                .isEqualTo(Collections.emptyList());

    }

    @Test
    void findByInstructor() {
        var member2 = memberRepository.save(MemberFixture.createActiveMember());
        var instructor2 = instructorRepository.save(InstructorFixture.createActiveInstructor(member2));

        Course course1 = courseRepository.save(CourseFixture.createCourse(instructor, "title"));
        Course course2 = courseRepository.save(CourseFixture.createCourse(instructor2, "title2"));

        List<Course> courses = courseRepository.findByInstructorId(instructor.getId());
        assertThat(courses).singleElement().isEqualTo(course1);

        List<Course> courses2 = courseRepository.findByInstructorId(instructor2.getId());
        assertThat(courses2).singleElement().isEqualTo(course2);

        List<Course> courses_2 = courseRepository.findByInstructor(instructor2);
        assertThat(courses_2).singleElement().isEqualTo(course2);
    }

    @Test
    void uniqueTitleAndInstructor() {

        courseRepository.save(CourseFixture.createCourse(instructor, "title"));

        assertThatThrownBy(() -> courseRepository.save(CourseFixture.createCourse(instructor, "title")))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void findByInstructorId() {
    }
}