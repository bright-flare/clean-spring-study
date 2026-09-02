package clean.spring.study.splearn.feature.member.infra.security;

import clean.spring.study.splearn.feature.member.adapter.security.SecurePasswordEncoder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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