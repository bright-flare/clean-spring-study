package clean.spring.study.splearn.domain.member;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

  @Test
  void profile() {

    new Profile("brightflare1");
    new Profile("brightflare2");
    new Profile("brightflare3");
    
  }
  
  @Test
  void profileFail() {

    assertThrows(IllegalArgumentException.class, () -> new Profile("sadfasjdfiasldfjsalidfjalsidfj"));
    assertThrows(IllegalArgumentException.class, () -> new Profile("bright-flare1"));
    assertThrows(IllegalArgumentException.class, () -> new Profile("심현섭"));

  }

  @Test
  void url() {
    
    Profile profile = new Profile("brightflare1");
    
    assertEquals("@brightflare1", profile.url());
  }
}