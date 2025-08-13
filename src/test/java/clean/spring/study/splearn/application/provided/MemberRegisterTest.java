package clean.spring.study.splearn.application.provided;

import clean.spring.study.splearn.application.SplearnTestConfiguration;
import clean.spring.study.splearn.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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
public record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

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
  void activate() {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
    entityManager.flush(); // 테스트 scope 와 실제 생성될 쿼리를 동일하게 확안하고 싶다,  영속성 컨텍스트에 저장된 엔티티를 DB에 반영
    entityManager.clear(); // 영속성 컨텍스트 초기화
    
    Member activatedMember = memberRegister.activate(member.getId());
    entityManager.flush();
    
    assertThat(activatedMember.getStatus()).isEqualTo(MemberStatus.ACTIVE);
  }

  @Test
  void memberRegisterRequestFail() {
    invalidRegister(new MemberRegisterRequest("orolsyeo@gmail.com", "brighdddddddddddddddddddddddt-flare", "sseob")); // username too long
    invalidRegister(new MemberRegisterRequest("orolsyeo@gmail.com", "b-flare", "short")); // password too short
    invalidRegister(new MemberRegisterRequest("orolsyeo", "b-flare", "sseddddddd")); // invalid email
  }

  private void invalidRegister(MemberRegisterRequest request) {
    assertThatThrownBy(() -> memberRegister.register(request))
            .isInstanceOf(ConstraintViolationException.class);
  }
}
