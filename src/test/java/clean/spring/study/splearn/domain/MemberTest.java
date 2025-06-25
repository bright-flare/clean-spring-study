package clean.spring.study.splearn.domain;

import org.apache.tomcat.util.http.parser.TE;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class MemberTest {

  @Test
  void createMember() {
    
    // given
    var member = new Member("bright-flare@splearn.app", "bright-flare", "password");
    
    // then
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
  }
  
  
}