package clean.spring.study.splearn.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import static java.util.Objects.requireNonNull;

@Getter
@ToString
public class Member {

  private Email email;
  
  private String nickname;

  private String passwordHash;

  private MemberStatus status;
  
  private Member() {}

  public static Member create(MemberCreateRequest createRequest, PasswordEncoder passwordEncoder) {
    
    Member member = new Member();
    
    member.email = new Email(createRequest.email());
    member.nickname = requireNonNull(createRequest.nickname());
    member.passwordHash = requireNonNull(passwordEncoder.encode(createRequest.password()));
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
