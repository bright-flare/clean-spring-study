package clean.spring.study.splearn.feature.member.infra.integration;

import clean.spring.study.splearn.feature.member.domain.Email;
import clean.spring.study.splearn.feature.member.adapter.integration.DummyEmailSender;
import org.junit.jupiter.api.Test;

class DummyEmailSenderTest {

  @Test
  void dummyEmailSender() {

    DummyEmailSender dummyEmailSender = new DummyEmailSender();
    dummyEmailSender.send(new Email("bright-flare@gmail.com"), "subject", "body");
    
  }
}