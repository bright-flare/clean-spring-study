package clean.spring.study.splearn.adapter.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SecurePasswordEncoderTest {

  @Test
  void securePasswordEncoder() {
    
    SecurePasswordEncoder securePasswordEncoder = new SecurePasswordEncoder();
    
    String password = "testPassword";
    String passwordHash = securePasswordEncoder.encode(password);

    assertThat(securePasswordEncoder.matches(password, passwordHash)).isTrue();
    assertThat(securePasswordEncoder.matches("wrong", passwordHash)).isFalse();
    
  }
}