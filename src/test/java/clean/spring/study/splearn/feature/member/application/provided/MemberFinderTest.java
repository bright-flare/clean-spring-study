package clean.spring.study.splearn.feature.member.application.provided;

import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.MemberFixture;
import clean.spring.study.splearn.support.stereotype.ApplicationServiceTest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ApplicationServiceTest
@RequiredArgsConstructor
class MemberFinderTest {

  final MemberFinder memberFinder;
  final MemberRegister memberRegister;
  final EntityManager entityManager;

  @Test
  void find() {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
    entityManager.flush();
    entityManager.clear();
    
    Member found = memberFinder.find(member.getId());
    
    assertThat(member.getId()).isEqualTo(found.getId());
  }
  
  @Test
  void findFail() {
    assertThatThrownBy(() -> memberFinder.find(999L))
            .isInstanceOf(IllegalArgumentException.class);
  }
  
}