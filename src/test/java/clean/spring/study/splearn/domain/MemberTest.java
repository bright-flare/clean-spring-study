package clean.spring.study.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class MemberTest {
  
  private final PasswordEncoder passwordEncoder = new PasswordEncoder() {
    @Override
    public String encode(String password) {
      return password; // For testing, we just return the password as is.
    }

    @Override
    public boolean matches(String password, String passwordHash) {
      return password.equals(passwordHash);
    }
    
  };
  
  Member member;
  
  @BeforeEach
  void setUp() {
    this.member = Member.create("bright-flare@splearn.app", "bright-flare", "password", passwordEncoder);
  }

  @Test
  void createMember() {
    
    // then
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    
  }

  @Test
  void constructorNullCheck() {
    
    assertThatThrownBy(() -> Member.create(null, "bright-flare", "password", passwordEncoder))
            .isInstanceOf(NullPointerException.class);
    
  }
  
  @Test
  void activate() {
    
    member.activate();
    
    assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
  }

  @Test
  void activateFail() {
  
    member.activate();
    
    assertThatThrownBy(member::activate)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("PENDING 상태가 아닙니다. 이미 활성화된 회원입니다.");
    
  }

  @Test
  void deactivate() {
    
    member.activate();
    
    member.deactivate();
    
    assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    
  }
  
  @Test
  void deactivateFail() {
  
    assertThatThrownBy(member::deactivate)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("ACTIVE 상태가 아닙니다. 이미 비활성화된 회원입니다.");
    
    member.activate();
    member.deactivate();
    
    assertThatThrownBy(member::deactivate)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("ACTIVE 상태가 아닙니다. 이미 비활성화된 회원입니다.");
    
  }
  
}