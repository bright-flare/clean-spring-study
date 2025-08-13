package clean.spring.study.splearn.adapter.integration;

import clean.spring.study.splearn.application.required.EmailSender;
import clean.spring.study.splearn.domain.Email;
import org.springframework.stereotype.Component;

@Component
public class DummyEmailSender implements EmailSender {
  
  @Override
  public void send(Email email, String subject, String content) {
    System.out.println("Dummy email sender: Sending email to " + email + " with subject: " + subject);
  }
  
}
