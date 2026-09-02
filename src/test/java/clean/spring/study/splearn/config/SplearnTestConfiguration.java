package clean.spring.study.splearn.config;

import clean.spring.study.splearn.feature.member.application.required.EmailSender;
import clean.spring.study.splearn.feature.member.domain.MemberFixture;
import clean.spring.study.splearn.feature.member.domain.PasswordEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration // DI 우선순위를 갖진 않는다. 테스트 빈을 생성할 뿐
public class SplearnTestConfiguration {

  @Bean
  EmailSender emailSender() {
    return (email, subject, content) -> System.out.println("email send !! " + email);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return MemberFixture.createPasswordEncoder();
  }

}
