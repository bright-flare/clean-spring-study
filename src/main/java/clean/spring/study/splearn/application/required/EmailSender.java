package clean.spring.study.splearn.application.required;

import clean.spring.study.splearn.domain.Email;

/**
 * 이메일을 전송하는 기능을 제공하는 인터페이스입니다.
 */
public interface EmailSender {
  void send(Email email);
}
