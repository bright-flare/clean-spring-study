package clean.spring.study.splearn.feature.instructor.domain;

import clean.spring.study.splearn.feature.instructor.application.dto.InstructorApplyRequest;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.MemberFixture;
import jakarta.validation.Valid;

public class InstructorFixture {

    public static Instructor createInstructor(Member member) {
        return Instructor.apply(member);
    }

    public static Instructor createInstructor() {
        return Instructor.apply(MemberFixture.createActiveMember());
    }

    public static Instructor createActiveInstructor() {
        Instructor instructor = createInstructor();
        instructor.approve();
        return instructor;
    }

    public static InstructorApplyRequest createApplyRequest(Member member) {
        return new InstructorApplyRequest(member.getId());
    }

}
