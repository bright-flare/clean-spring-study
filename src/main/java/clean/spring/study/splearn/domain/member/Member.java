package clean.spring.study.splearn.domain.member;

import clean.spring.study.splearn.domain.shared.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.util.Assert;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Entity
@Getter
@ToString(callSuper = true, exclude = "detail")
@NaturalIdCache
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Member extends AbstractEntity {

  @NaturalId // 자연 키로 사용, 이메일은 유일해야 함, unique 제약 조건을 추가할 수 있음
  private Email email;

  private String nickname;

  private String passwordHash;

  private MemberStatus status;

  private MemberDetail detail;

  public static Member register(MemberRegisterRequest registerRequest, PasswordEncoder passwordEncoder) {

    Member member = new Member();

    member.email = new Email(registerRequest.email());
    member.nickname = requireNonNull(registerRequest.nickname());
    member.passwordHash = requireNonNull(passwordEncoder.encode(registerRequest.password()));
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

  public boolean varifyPassword(String password, PasswordEncoder passwordEncoder) {
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
