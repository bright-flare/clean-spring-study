package clean.spring.study.splearn.application.member.provided;

import clean.spring.study.splearn.application.SplearnTestConfiguration;
import clean.spring.study.splearn.domain.member.*;
import jakarta.persistence.EntityManager;
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
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

  @Test
  void register() {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

    assertThat(member.getId()).isNotNull();
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    assertThat(member.getDetail().getRegisteredAt()).isNotNull();
    
  }
  
  @Test
  void duplicateEmailFail() {
    
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

    assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
            .isInstanceOf(DuplicateEmailException.class);
    
  }
  
  @Test
  void activate() {
    Member member = registerMember();

    Member activatedMember = memberRegister.activate(member.getId());
    entityManager.flush();
    
    assertThat(activatedMember.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    assertThat(activatedMember.getDetail().getActivatedAt()).isNotNull();
  }

  private Member registerMember() {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
    entityManager.flush(); // 테스트 scope 와 실제 생성될 쿼리를 동일하게 확안하고 싶다,  영속성 컨텍스트에 저장된 엔티티를 DB에 반영
    entityManager.clear(); // 영속성 컨텍스트 초기화
    return member;
  }
  
  private Member registerMember(String email) {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest(email));
    entityManager.flush(); // 테스트 scope 와 실제 생성될 쿼리를 동일하게 확안하고 싶다,  영속성 컨텍스트에 저장된 엔티티를 DB에 반영
    entityManager.clear(); // 영속성 컨텍스트 초기화
    return member;
  }

  @Test
  void deactivate() {
    Member member = registerMember();

    memberRegister.activate(member.getId());
    entityManager.flush();
    entityManager.clear();

    Member deactivate = memberRegister.deactivate(member.getId());

    assertThat(deactivate.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    assertThat(deactivate.getDetail().getDeactivatedAt()).isNotNull();
  }

  @Test
  void updateInfo() {
    Member member = registerMember();
    
    memberRegister.activate(member.getId());
    entityManager.flush();
    entityManager.clear();

    member = memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("변경된이름", "orol", "자기소개입니다링 "));
    
    assertThat(member.getDetail().getProfile().address()).isEqualTo("orol");
  }
  
  @Test
  void updateInfoFail() {
    Member member = registerMember();
    
    memberRegister.activate(member.getId());
    memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("변경된이름", "orol", "자기소개입니다링 "));
    entityManager.flush();
    entityManager.clear();
    
    Member member2 = registerMember("orolseyo@gmail.com");
    memberRegister.activate( member2.getId());
    entityManager.flush();
    entityManager.clear();

    assertThatThrownBy(
            () -> memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("심현섭친구", "orol", "심현섭 "))
    ).isInstanceOf(DuplicateProfileException.class);

    memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("심현섭친구", "orolsyeo", "심현섭 "));
    memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("심현섭친구", "orol", "심현섭 "));
    memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("심현섭친구", "", "심현섭 "));
    memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("심현섭친구", "orol", "심현섭 "));
    
    assertThatThrownBy(
            () -> memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("심현섭친구", "orolsyeo", "심현섭 "))
    ).isInstanceOf(DuplicateProfileException.class);
    
  }

  @Test
  void memberRegisterRequestFail() {
    checkValidation(new MemberRegisterRequest("orolsyeo@gmail.com", "brighdddddddddddddddddddddddt-flare", "sseob")); // username too long
    checkValidation(new MemberRegisterRequest("orolsyeo@gmail.com", "b-flare", "short")); // password too short
    checkValidation(new MemberRegisterRequest("orolsyeo", "b-flare", "sseddddddd")); // invalid email
  }

  private void checkValidation(MemberRegisterRequest request) {
    assertThatThrownBy(() -> memberRegister.register(request))
            .isInstanceOf(ConstraintViolationException.class);
  }
}
