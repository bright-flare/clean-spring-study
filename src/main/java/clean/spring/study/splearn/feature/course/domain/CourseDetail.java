package clean.spring.study.splearn.feature.course.domain;

import clean.spring.study.splearn.feature.shared.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString(callSuper = true, exclude = {"course"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseDetail extends AbstractEntity {

    @Column(length = 500)
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;

    private LocalDateTime archivedAt;

    @OneToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    CourseDetail(Course course, String description) {
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.course = course;
    }

    void publish() {
        this.publishedAt = LocalDateTime.now();
    }

    void archive() {
        this.archivedAt = LocalDateTime.now();
    }

    void updateInfo(CourseUpdateInfo info) {
        this.description = info.description();
    }
}
