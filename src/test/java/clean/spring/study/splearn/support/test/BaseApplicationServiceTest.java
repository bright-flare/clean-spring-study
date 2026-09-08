package clean.spring.study.splearn.support.test;

import clean.spring.study.splearn.feature.course.application.provided.CourseCreator;
import clean.spring.study.splearn.feature.course.domain.Course;
import clean.spring.study.splearn.feature.course.domain.CourseFixture;
import clean.spring.study.splearn.feature.instructor.application.provided.InstructorApplication;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.feature.instructor.domain.InstructorFixture;
import clean.spring.study.splearn.feature.member.application.provided.MemberRegister;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.MemberFixture;
import clean.spring.study.splearn.support.stereotype.ApplicationServiceTest;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationServiceTest
public class BaseApplicationServiceTest {

    @Autowired
    MemberRegister memberRegister;

    @Autowired
    InstructorApplication instructorApplication;

    @Autowired
    CourseCreator courseCreator;

    protected Member member;
    protected Instructor instructor;
    protected Course course;

    @NonNull
    protected Instructor prepareInstructor() {
        prepareMember();

        this.instructor = instructorApplication.apply(InstructorFixture.createApplyRequest(member));
        instructor.approve();

        return this.instructor;
    }

    protected Member prepareMember() {
        this.member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        this.member.activate();

        return this.member;
    }

    protected Course prepareCourse() {
        prepareInstructor();

        course = courseCreator.create(CourseFixture.createCourseCreateRequest(instructor.getId(), "title"));
        course.updateInfo(CourseFixture.createCourseUpdateRequest(null).toInfo());

        return this.course;
    }
}
