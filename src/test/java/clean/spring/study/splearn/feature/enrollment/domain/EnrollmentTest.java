package clean.spring.study.splearn.feature.enrollment.domain;

import clean.spring.study.splearn.feature.course.domain.Course;
import clean.spring.study.splearn.feature.course.domain.CourseFixture;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.MemberFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentTest {

    @Test
    void enroll() {
        Member member = MemberFixture.createActiveMember();
        Course course = CourseFixture.createPublishedCourse();

        Enrollment enroll = Enrollment.enroll(member, course);

    }
}