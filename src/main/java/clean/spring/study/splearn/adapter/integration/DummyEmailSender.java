package clean.spring.study.splearn.adapter.integration;

import clean.spring.study.splearn.application.member.required.EmailSender;
import clean.spring.study.splearn.domain.member.Email;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

@Component
@Fallback
public class DummyEmailSender implements EmailSender {
  
  @Override
  public void send(Email email, String subject, String content) {
    System.out.println("Dummy email sender: Sending email to " + email + " with subject: " + subject);
  }
  
}
