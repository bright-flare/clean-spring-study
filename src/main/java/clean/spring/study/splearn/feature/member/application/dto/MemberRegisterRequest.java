package clean.spring.study.splearn.feature.member.application.dto;

import clean.spring.study.splearn.feature.member.domain.MemberRegisterInfo;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record MemberRegisterRequest(
        @Email String email,
        @Length(min = 5, max = 20) String nickname,
        @Length(min = 8, max = 100) String password) {

  public static MemberRegisterRequest of(String email, String nickname, String password) {
    return new MemberRegisterRequest(email, nickname, password);
  }

    public MemberRegisterInfo toInfo() {
      return new MemberRegisterInfo(email, nickname, password);
    }

}
