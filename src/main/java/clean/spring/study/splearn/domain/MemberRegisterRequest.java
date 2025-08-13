package clean.spring.study.splearn.domain;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record MemberRegisterRequest(
        @Email String email,
        @Length(min = 5, max = 20) String nickname,
        @Length(min = 8, max = 100) String password) {

  static MemberRegisterRequest of(String email, String nickname, String password) {
    return new MemberRegisterRequest(email, nickname, password);
  }

}
