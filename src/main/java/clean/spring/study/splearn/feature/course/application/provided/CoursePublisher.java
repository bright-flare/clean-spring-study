package clean.spring.study.splearn.feature.course.application.provided;

import clean.spring.study.splearn.feature.course.domain.Course;
import jakarta.validation.Valid;

/**
 * 강의 공개와 관련된 작업
 */
public interface CoursePublisher {
    Course submitForReview(Long courseId);

    Course publish(Long courseId);

    Course archive(Long courseId);
}
