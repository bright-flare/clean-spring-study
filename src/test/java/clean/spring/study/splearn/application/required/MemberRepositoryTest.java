package clean.spring.study.splearn.application.required;

import clean.spring.study.splearn.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static clean.spring.study.splearn.domain.MemberFixture.createMemberRegister;
import static clean.spring.study.splearn.domain.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Test
  void createMember() {
    Member member = Member.register(createMemberRegister(), createPasswordEncoder());

    assertThat(member.getId()).isNull();
    
    Member save = memberRepository.save(member);
    
    assertThat(save.getId()).isNotNull();
  }
  
}