package clean.spring.study.splearn.application.instructor.provided;

import clean.spring.study.splearn.domain.instructor.Instructor;
import clean.spring.study.splearn.domain.member.Member;

import java.util.Optional;

/**
 * 강사 조회
 */
public interface InstructorFinder {

    Instructor findById(Long instructorId);

    Optional<Instructor> findByMember(Long memberId);

    default Optional<Instructor> findByMember(Member member) {
        return findByMember(member.getId());
    }

}
