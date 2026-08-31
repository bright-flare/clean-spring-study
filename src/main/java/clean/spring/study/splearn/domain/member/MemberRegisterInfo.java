package clean.spring.study.splearn.domain.member;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record MemberRegisterInfo(
        @Email String email,
        @Length(min = 5, max = 20) String nickname,
        @Length(min = 8, max = 100) String password) {
}
