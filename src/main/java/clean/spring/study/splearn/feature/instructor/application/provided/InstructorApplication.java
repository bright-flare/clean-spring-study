package clean.spring.study.splearn.feature.instructor.application.provided;

import clean.spring.study.splearn.feature.instructor.application.dto.InstructorApplyRequest;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import jakarta.validation.Valid;

/**
 * 강사 신청
 */
public interface InstructorApplication {

    Instructor apply(@Valid InstructorApplyRequest request);

    Instructor approve(Long memberId);

    Instructor reject(Long memberId);

}
