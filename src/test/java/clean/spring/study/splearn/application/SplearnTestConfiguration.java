package clean.spring.study.splearn.application;

import clean.spring.study.splearn.application.member.required.EmailSender;
import clean.spring.study.splearn.domain.member.MemberFixture;
import clean.spring.study.splearn.domain.member.PasswordEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
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
