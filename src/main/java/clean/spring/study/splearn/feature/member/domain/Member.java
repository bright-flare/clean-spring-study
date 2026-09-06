package clean.spring.study.splearn.feature.member.domain;

import clean.spring.study.splearn.feature.shared.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Entity
@Table(name = "member", uniqueConstraints = {
    @UniqueConstraint(name = "uk_member_email_address", columnNames = "email_address"),
    @UniqueConstraint(name = "uk_member_detail_id", columnNames = "detail_id")
})
@Getter
@ToString(callSuper = true, exclude = "detail")
@NaturalIdCache // Persist context가 아닌, 2차 캐시에 사용된다.
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Member extends AbstractEntity {

  @Embedded
  @NaturalId // 자연 키로 사용, 이메일은 유일해야 함, unique 제약 조건을 추가할 수 있음
  private Email email;

  @Column(nullable = false, length = 100)
  private String nickname;

  @Column(nullable = false, length = 200)
  private String passwordHash;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50, columnDefinition = "varchar(50)")
  private MemberStatus status;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "detail_id")
  private MemberDetail detail;

  public static Member register(MemberRegisterInfo registerInfo, PasswordEncoder passwordEncoder) {

    Member member = new Member();

    member.email = new Email(registerInfo.email());
    member.nickname = requireNonNull(registerInfo.nickname());
    member.passwordHash = requireNonNull(passwordEncoder.encode(registerInfo.password()));
    member.status = MemberStatus.PENDING;
    member.detail = MemberDetail.create();

    return member;
  }

  public void activate() {

    state(this.status == MemberStatus.PENDING, "PENDING 상태가 아닙니다. 이미 활성화된 회원입니다.");

    this.status = MemberStatus.ACTIVE;
    this.detail.updateActivatedAt();
  }

  public void deactivate() {

    state(this.status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다. 이미 비활성화된 회원입니다.");

    this.status = MemberStatus.DEACTIVATED;
    this.detail.updateDeactivatedAt();
  }

  public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(password, this.passwordHash);
  }

  public void changePassword(String password, PasswordEncoder passwordEncoder) {
    this.passwordHash = passwordEncoder.encode(requireNonNull(password));
  }

  public void updateInfo(MemberInfoUpdateRequest updateRequest) {
    
    state(this.status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다. 회원 정보를 수정할 수 없습니다.");
    
    this.nickname = updateRequest.nickname();
    this.detail.updateInfo(updateRequest);
  }

  public boolean isActive() {
    return this.status == MemberStatus.ACTIVE;
  }

}
