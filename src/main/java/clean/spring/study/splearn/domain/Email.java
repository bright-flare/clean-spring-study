package clean.spring.study.splearn.domain;

import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

/**
 * hibernate 6.2 이상부터는 @Embeddable로 선언된 record 타입을 지원합니다.
 */
@Embeddable
public record Email(String email) {
  
  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

  public Email {

    requireNonNull(email, "Email cannot be null");
    
    if (!EMAIL_PATTERN.matcher(email).matches()) {
      throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
    }

  }

}
