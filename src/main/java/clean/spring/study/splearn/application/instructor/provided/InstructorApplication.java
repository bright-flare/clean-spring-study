package clean.spring.study.splearn.application.instructor.provided;

import clean.spring.study.splearn.application.instructor.dto.InstructorApplyRequest;
import clean.spring.study.splearn.domain.instructor.Instructor;
import jakarta.validation.Valid;

/**
 * 강사 신청
 */
public interface InstructorApplication {

    Instructor apply(@Valid InstructorApplyRequest request);

    Instructor approve(Long memberId);

    Instructor reject(Long memberId);

}
