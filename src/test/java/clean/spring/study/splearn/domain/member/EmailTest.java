package clean.spring.study.splearn.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {


  @Test
  void equality() {

    Email email = new Email("orolsyeo@gmail.com");
    Email email2 = new Email("orolsyeo@gmail.com");
    
    assertThat(email).isEqualTo(email2);

  }
  
  
}