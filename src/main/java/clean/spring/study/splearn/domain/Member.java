package clean.spring.study.splearn.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.Objects;

@Getter
@ToString
public class Member {

  String email;
  
  String nickname;

  String passwordHash;

  MemberStatus status;

  private Member(String email, String nickname, String passwordHash) {
    this.email = Objects.requireNonNull(email);
    this.nickname = Objects.requireNonNull(nickname);
    this.passwordHash = Objects.requireNonNull(passwordHash);
    this.status = MemberStatus.PENDING;
  }
  
  public static Member create(String email, String nickname, String password, PasswordEncoder passwordEncoder) {
    return new Member(email, nickname, passwordEncoder.encode(password));
  }
  
  public void activate() {
    
    Assert.state(this.status == MemberStatus.PENDING, "PENDING 상태가 아닙니다. 이미 활성화된 회원입니다.");
    
    this.status = MemberStatus.ACTIVE;
  }

  public void deactivate() {
    
    Assert.state(this.status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다. 이미 비활성화된 회원입니다.");
    
    this.status = MemberStatus.DEACTIVATED;
  }
}
