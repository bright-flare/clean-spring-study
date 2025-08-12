package clean.spring.study.splearn.application.provided;

import clean.spring.study.splearn.application.SplearnTestConfiguration;
import clean.spring.study.splearn.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) // junit-platform.properties 설정으로 대체가능.
public record MemberRegisterTest(MemberRegister memberRegister) {

  @Test
  void register() {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

    assertThat(member.getId()).isNotNull();
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    
  }
  
  @Test
  void duplicateEmailFail() {
    
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

    assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
            .isInstanceOf(DuplicateEmailException.class);
    
  }

  @Test
  void memberRegisterRequestFail() {

    MemberRegisterRequest request = new MemberRegisterRequest("orolsyeo@gmail.com", "bright-flare", "sseob");

    memberRegister.register(request);
    
  }
}
