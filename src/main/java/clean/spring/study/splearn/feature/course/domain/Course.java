package clean.spring.study.splearn.feature.course.domain;

import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.feature.shared.domain.AbstractEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Table(
        name = "course",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_instructor_title",
                columnNames = {
                        "instructorId",
                        "title"
                }
        )
)
@Entity
@Getter
@ToString(callSuper = true, exclude = {})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends AbstractEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, columnDefinition = "varchar(20)")
    private CourseStatus status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "course")
    private CourseDetail detail;

    public Course(String title, Instructor instructor, @Nullable String description) {

        instructor.ensureActive();

        this.title = requireNonNull(title);
        this.instructor = requireNonNull(instructor);
        this.status = CourseStatus.DRAFT;
        this.detail = new CourseDetail(this, description);
    }

    public void submitForReview() {

        state(status == CourseStatus.DRAFT, "DRAFT 상태가 아닙니다.");
        state(StringUtils.hasText(detail.getDescription()), "강의 소개가 등록되지 않았습니다.");

        this.status = CourseStatus.IN_REVIEW;

    }

    public void publish() {
        state(status == CourseStatus.IN_REVIEW, "IN_REVIEW 상태가 아닙니다.");

        this.status = CourseStatus.PUBLISHED;
        this.detail.publish();
    }

    public void archive() {
        state(status == CourseStatus.PUBLISHED, "PUBLISHED 상태가 아닙니다.");

        this.status = CourseStatus.ARCHIVED;
        this.detail.archive();
    }

    public boolean isPublished() {
        return status == CourseStatus.PUBLISHED;
    }

    public void ensurePublished() {
        state(status == CourseStatus.PUBLISHED, "PUBLISHED 상태가 아닙니다.");
    }

    public void updateInfo(CourseUpdateInfo info) {
        this.title = Objects.requireNonNull(info.title());
        this.detail.updateInfo(info);
    }

}
