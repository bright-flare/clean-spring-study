package clean.spring.study.splearn.feature.instructor.application.dto;

import jakarta.validation.constraints.NotNull;

public record InstructorApplyRequest(
        @NotNull Long memberId
) {
}
