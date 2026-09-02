package clean.spring.study.splearn.feature.member.application.provided;

import clean.spring.study.splearn.feature.member.application.dto.MemberLoginRequest;
import clean.spring.study.splearn.feature.member.application.provided.exception.LoginFailedException;
import clean.spring.study.splearn.feature.member.domain.Member;
import jakarta.validation.Valid;

/**
 * 회원 인증
 * - ACTIVE 상태인 회원만 로그인할 수 있다.
 */
public interface MemberAuthenticator {
    Member login(@Valid MemberLoginRequest loginRequest) throws LoginFailedException;
}
