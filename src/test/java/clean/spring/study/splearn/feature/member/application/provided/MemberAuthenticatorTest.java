package clean.spring.study.splearn.feature.member.application.provided;

import clean.spring.study.splearn.feature.member.application.dto.MemberLoginRequest;
import clean.spring.study.splearn.feature.member.application.dto.MemberRegisterRequest;
import clean.spring.study.splearn.feature.member.application.provided.exception.LoginFailedException;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.MemberFixture;
import clean.spring.study.splearn.support.stereotype.ApplicationServiceTest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@ApplicationServiceTest
@RequiredArgsConstructor
class MemberAuthenticatorTest {

    private final MemberAuthenticator memberAuthenticator;
    private final MemberRegister memberRegister;

    @Test
    void login() {

        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
        Member member = memberRegister.register(request);
        member.activate();

        Member login = memberAuthenticator.login(new MemberLoginRequest(request.email(), request.password()));

        Assertions.assertThat(member).isEqualTo(login);

    }

    @Test
    void loginFailNotActive() {

        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest);

        Assertions.assertThatThrownBy(() ->
                memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), registerRequest.password()))
        ).isInstanceOf(LoginFailedException.class);

    }

    @Test
    void loginFailNotEmailNotExists() {

        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest).activate();

        Assertions.assertThatThrownBy(() ->
                memberAuthenticator.login(new MemberLoginRequest("notExitst@email.com", registerRequest.password()))
        ).isInstanceOf(LoginFailedException.class);

    }

    @Test
    void loginFailedWrongPassword() {

        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest).activate();

        Assertions.assertThatThrownBy(() ->
                memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), "as8dfsa8dfsd8"))
        ).isInstanceOf(LoginFailedException.class);

    }

}