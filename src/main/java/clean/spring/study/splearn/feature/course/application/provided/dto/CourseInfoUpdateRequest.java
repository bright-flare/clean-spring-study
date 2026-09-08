package clean.spring.study.splearn.feature.course.application.provided.dto;

import clean.spring.study.splearn.feature.course.domain.CourseUpdateInfo;
import jakarta.validation.constraints.Size;

public record CourseInfoUpdateRequest(
        @Size(min = 2, max = 100) String title,
        @Size(max = 500) String description
) {
    public CourseUpdateInfo toInfo() {
        return new CourseUpdateInfo(title, description);
    }

}
