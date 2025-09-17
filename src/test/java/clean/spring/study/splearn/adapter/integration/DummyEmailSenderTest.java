package clean.spring.study.splearn.adapter.integration;

import clean.spring.study.splearn.domain.member.Email;
import org.junit.jupiter.api.Test;

class DummyEmailSenderTest {

  @Test
  void dummyEmailSender() {

    DummyEmailSender dummyEmailSender = new DummyEmailSender();
    dummyEmailSender.send(new Email("bright-flare@gmail.com"), "subject", "body");
    
  }
}