package clean.spring.study.splearn.feature.enrollment.domain;

import clean.spring.study.splearn.feature.course.domain.Course;
import clean.spring.study.splearn.feature.course.domain.CourseStatus;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.shared.domain.AbstractEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString(callSuper = true, exclude = {"member", "course"})
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(name = "", columnNames = {"member_id", "course_id"}))
public class Enrollment extends AbstractEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EnrollmentStatus status;

    @Column(nullable = false)
    private LocalDateTime enrolledAt;

    private LocalDateTime completedAt;

    public static Enrollment enroll(Member member, Course course) {
        member.ensureActive();
        course.ensurePublished();

        Enrollment enrollment = new Enrollment();
        enrollment.member = member;
        enrollment.course = course;
        enrollment.status = EnrollmentStatus.ENROLLED;
        enrollment.enrolledAt = LocalDateTime.now();

        return enrollment;
    }

}
