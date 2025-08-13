package clean.spring.study.splearn.adapter.integration;

import clean.spring.study.splearn.domain.Email;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DummyEmailSenderTest {

  @Test
  void dummyEmailSender() {

    DummyEmailSender dummyEmailSender = new DummyEmailSender();
    dummyEmailSender.send(new Email("bright-flare@gmail.com"), "subject", "body");
    
  }
}