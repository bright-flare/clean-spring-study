package clean.spring.study.splearn.domain.instructor;

import clean.spring.study.splearn.domain.member.Member;
import clean.spring.study.splearn.domain.shared.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import static org.springframework.util.Assert.state;

@Entity
@Getter
@ToString(callSuper = true, exclude = "member")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Instructor extends AbstractEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private InstructorStatus status;

    public static Instructor apply(Member member) {
        state(member.isActive(), "등록 완료 상태가 아닌 회원은 강사 신청을 할 수 없습니다.");
        Instructor instructor = new Instructor();
        instructor.member = member;
        instructor.status = InstructorStatus.PENDING;
        return instructor;
    }

    public void approve() {
        state(status == InstructorStatus.PENDING, "강사의 상태가 PENDING이 아닙니다.");
        this.status = InstructorStatus.ACTIVE;
    }

    public void reject() {
        state(status == InstructorStatus.PENDING, "강사의 상태가 PENDING이 아닙니다.");
        this.status = InstructorStatus.REJECTED;
    }

    public boolean isActive() {
        return status == InstructorStatus.ACTIVE;
    }

    public void ensureActive() {

        state(isActive(), "ACTIVE 상태가 아닙니다.");

    }
}
