package clean.spring.study.splearn.feature.member.application;

import clean.spring.study.splearn.feature.member.application.provided.MemberAuthenticator;
import clean.spring.study.splearn.feature.member.application.dto.MemberLoginRequest;
import clean.spring.study.splearn.feature.member.application.provided.exception.LoginFailedException;
import clean.spring.study.splearn.feature.member.application.required.MemberRepository;
import clean.spring.study.splearn.feature.member.domain.Email;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class MemberAuthenticationService implements MemberAuthenticator {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member login(MemberLoginRequest loginRequest) throws LoginFailedException {

        Member member = memberRepository.findByEmail(new Email(loginRequest.email()))
                .orElseThrow(LoginFailedException::new);

        if (!member.isActive()) {
            throw new LoginFailedException();
        }

        if (!member.verifyPassword(loginRequest.password(), passwordEncoder)) {
            throw new LoginFailedException();
        }

        return member;
    }

}
