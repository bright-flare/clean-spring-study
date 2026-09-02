package clean.spring.study.splearn.feature.member.application.required;

import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.MemberFixture;
import clean.spring.study.splearn.feature.member.domain.MemberStatus;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static clean.spring.study.splearn.feature.member.domain.MemberFixture.createMemberRegisterRequest;
import static clean.spring.study.splearn.feature.member.domain.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;
  
  @Autowired
  EntityManager entityManager;

  @Test
  void createMember() {
    Member member = Member.register(MemberFixture.createMemberRegisterRequest().toInfo(), createPasswordEncoder());

    assertThat(member.getId()).isNull();
    
    Member save = memberRepository.save(member);
    
    assertThat(save.getId()).isNotNull();

    entityManager.flush();
    entityManager.clear();

    assertThat(save.getDetail()).isNotNull();
    
    Member found = memberRepository.findById(member.getId()).orElseThrow();
    assertThat(found.getStatus()).isEqualTo(MemberStatus.PENDING);
    assertThat(found.getDetail().getRegisteredAt()).isNotNull();
    
  }
  
  @Test
  void duplicateEmailFail() {
    
    Member member = Member.register(MemberFixture.createMemberRegisterRequest().toInfo(), createPasswordEncoder());
    memberRepository.save(member);
    
    Member member2 = Member.register(MemberFixture.createMemberRegisterRequest().toInfo(), createPasswordEncoder());
    assertThatThrownBy(() -> memberRepository.save(member2))
            .isInstanceOf(DataIntegrityViolationException.class);
  }
  
}