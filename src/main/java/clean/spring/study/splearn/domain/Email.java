package clean.spring.study.splearn.domain;

import static java.util.Objects.requireNonNull;

public record Email(String email) {

  public Email {

    requireNonNull(email, "Email cannot be null");
    
    if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
      throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
    }

  }

}
