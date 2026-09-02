package clean.spring.study.splearn.feature.instructor.application.provided;

import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.feature.member.domain.Member;

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
