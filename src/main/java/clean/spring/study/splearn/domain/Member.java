package clean.spring.study.splearn.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Member {

  String email;
  
  String nickname;

  String passwordHash;

  MemberStatus status;

  public Member(String email, String nickname, String passwordHash) {
    this.email = Objects.requireNonNull(email);
    this.nickname = Objects.requireNonNull(nickname);
    this.passwordHash = Objects.requireNonNull(passwordHash);
    this.status = MemberStatus.PENDING;
  }
  
}
