package clean.spring.study.splearn.application.member.provided;

import clean.spring.study.splearn.application.member.dto.MemberLoginRequest;
import clean.spring.study.splearn.application.member.provided.exception.LoginFailedException;
import clean.spring.study.splearn.domain.member.Member;
import jakarta.validation.Valid;

/**
 * 회원 인증
 * - ACTIVE 상태인 회원만 로그인할 수 있다.
 */
public interface MemberAuthenticator {
    Member login(@Valid MemberLoginRequest loginRequest) throws LoginFailedException;
}
