package clean.spring.study.splearn.application.required;

import clean.spring.study.splearn.domain.Member;
import clean.spring.study.splearn.domain.MemberFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static clean.spring.study.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static clean.spring.study.splearn.domain.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Test
  void createMember() {
    Member member = Member.register(MemberFixture.createMemberRegisterRequest(), createPasswordEncoder());

    assertThat(member.getId()).isNull();
    
    Member save = memberRepository.save(member);
    
    assertThat(save.getId()).isNotNull();
  }
  
  @Test
  void duplicateEmailFail() {
    
    Member member = Member.register(MemberFixture.createMemberRegisterRequest(), createPasswordEncoder());
    memberRepository.save(member);
    
    Member member2 = Member.register(MemberFixture.createMemberRegisterRequest(), createPasswordEncoder());
    assertThatThrownBy(() -> memberRepository.save(member2))
            .isInstanceOf(DataIntegrityViolationException.class);
  }
  
}