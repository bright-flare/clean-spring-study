package clean.spring.study.splearn.application.instructor.dto;

import jakarta.validation.constraints.NotNull;

public record InstructorApplyRequest(
        @NotNull Long memberId
) {
}
