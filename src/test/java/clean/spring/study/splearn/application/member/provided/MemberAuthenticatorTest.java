package clean.spring.study.splearn.application.member.provided;

import clean.spring.study.splearn.application.SplearnTestConfiguration;
import clean.spring.study.splearn.application.member.dto.MemberLoginRequest;
import clean.spring.study.splearn.application.member.dto.MemberRegisterRequest;
import clean.spring.study.splearn.application.member.provided.exception.LoginFailedException;
import clean.spring.study.splearn.domain.member.Member;
import clean.spring.study.splearn.domain.member.MemberFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
class MemberAuthenticatorTest {

    @Autowired
    private MemberAuthenticator memberAuthenticator;

    @Autowired
    private MemberRegister memberRegister;

    @Test
    void login() {

        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(request).activate();

        Member login = memberAuthenticator.login(new MemberLoginRequest(request.email(), request.password()));

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