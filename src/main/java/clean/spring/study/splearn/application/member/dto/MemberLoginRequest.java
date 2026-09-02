package clean.spring.study.splearn.application.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record MemberLoginRequest(
        @Email String email,
        @Size(min = 8, max = 100) String password
) {
}
