package clean.spring.study.splearn.feature.instructor.application.provided;

import clean.spring.study.splearn.feature.instructor.application.dto.InstructorApplyRequest;
import clean.spring.study.splearn.feature.instructor.application.required.InstructorRepository;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.feature.instructor.domain.InstructorFixture;
import clean.spring.study.splearn.feature.instructor.domain.InstructorStatus;
import clean.spring.study.splearn.feature.member.application.required.MemberRepository;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.MemberFixture;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
class InstructorApplicationTest {

    final InstructorApplication instructorApplication;
    final InstructorRepository instructorRepository;
    final MemberRepository memberRepository;

    @Test
    void apply() {
        Member member = MemberFixture.createActiveMember();
        memberRepository.save(member);
        Instructor instructor = instructorApplication.apply(new InstructorApplyRequest(member.getId()));

        assertThat(instructor.getMember()).isNotNull();
        assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.PENDING);

        instructorRepository.findById(instructor.getId()).get();

    }

    @Test
    void duplicateApply() {

        Member member = MemberFixture.createActiveMember();

        memberRepository.save(member);

        instructorApplication.apply(InstructorFixture.createApplyRequest(member));

        Assertions.assertThatThrownBy(() -> instructorApplication.apply(InstructorFixture.createApplyRequest(member)))
                .isInstanceOf(DuplicateInstructorApplicationException.class);

    }

    @Test
    void approve() {

        Instructor instructor = preparePendingInstructor();

        Instructor approve = instructorApplication.approve(instructor.getId());

        assertThat(approve.getStatus()).isEqualTo(InstructorStatus.ACTIVE);

    }

    @Test
    void reject() {

        Instructor instructor = preparePendingInstructor();

        Instructor reject = instructorApplication.reject(instructor.getId());

        assertThat(reject.getStatus()).isEqualTo(InstructorStatus.REJECTED);
    }

    private Instructor preparePendingInstructor() {
        Member member = MemberFixture.createActiveMember();
        memberRepository.save(member);
        return instructorApplication.apply(InstructorFixture.createApplyRequest(member));
    }

}