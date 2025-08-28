package clean.spring.study.splearn.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.util.Assert;

import static java.util.Objects.requireNonNull;

@Entity
@Getter
@ToString(callSuper = true)
@NaturalIdCache
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Member extends AbstractEntity {

  @NaturalId // 자연 키로 사용, 이메일은 유일해야 함, unique 제약 조건을 추가할 수 있음
  private Email email;

  private String nickname;

  private String passwordHash;

  private MemberStatus status;

  public static Member register(MemberRegisterRequest registerRequest, PasswordEncoder passwordEncoder) {

    Member member = new Member();

    member.email = new Email(registerRequest.email());
    member.nickname = requireNonNull(registerRequest.nickname());
    member.passwordHash = requireNonNull(passwordEncoder.encode(registerRequest.password()));
    member.status = MemberStatus.PENDING;

    return member;
  }

  public void activate() {

    Assert.state(this.status == MemberStatus.PENDING, "PENDING 상태가 아닙니다. 이미 활성화된 회원입니다.");

    this.status = MemberStatus.ACTIVE;
  }

  public void deactivate() {

    Assert.state(this.status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다. 이미 비활성화된 회원입니다.");

    this.status = MemberStatus.DEACTIVATED;
  }

  public boolean varifyPassword(String password, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(password, this.passwordHash);
  }

  public void changeNickname(String nickname) {
    this.nickname = requireNonNull(nickname);
  }

  public void changePassword(String password, PasswordEncoder passwordEncoder) {
    this.passwordHash = passwordEncoder.encode(requireNonNull(password));
  }

  public boolean isActive() {
    return this.status == MemberStatus.ACTIVE;
  }

}
