package clean.spring.study.splearn.application.provided;

import clean.spring.study.splearn.application.required.EmailSender;
import clean.spring.study.splearn.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberRegisterTest {
  
  @Autowired
  private MemberRegister memberRegister;

  @Test
  void register() {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

    assertThat(member.getId()).isNotNull();
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    
  }

  @TestConfiguration
  static class MemberTestConfiguration {
    
    @Bean
    EmailSender emailSender() {
      return (email, subject, content) -> System.out.println("email send !! " + email);
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
      return MemberFixture.createPasswordEncoder();
    }
    
  }
}
