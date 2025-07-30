package clean.spring.study.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class MemberTest {
  
  private final PasswordEncoder passwordEncoder = new PasswordEncoder() {
    @Override
    public String encode(String password) {
      return password.toUpperCase(); // For testing, we just return the password as is.
    }

    @Override
    public boolean matches(String password, String passwordHash) {
      return encode(password).equals(passwordHash);
    }
    
  };
  
  Member member;
  
  @BeforeEach
  void setUp() {
    MemberRegisterRequest request = MemberRegisterRequest.of("bright-flare@splearn.app", "bright-flare", "password");
    this.member = Member.register(request, passwordEncoder);
  }

  @Test
  void registerMember() {
    
    // then
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    
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

  @Test
  void verifyPassword() {
    assertThat(member.varifyPassword("password", passwordEncoder)).isTrue();
    assertThat(member.varifyPassword("afsd", passwordEncoder)).isFalse();
  }

  @Test
  void changePassword() {

    assertThat(member.varifyPassword("password", passwordEncoder)).isTrue();
    
    member.changePassword("new-password", passwordEncoder);
    assertThat(member.varifyPassword("new-password", passwordEncoder)).isTrue();
    
  }

  @Test
  void changeNickname() {
    assertThat(member.getNickname()).isEqualTo("bright-flare");
    
    member.changeNickname("lumiflare");
    assertThat(member.getNickname()).isEqualTo("lumiflare");
  }

  @Test
  void shouldBeActive() {
    assertThat(this.member.isActive()).isFalse();
    
    member.activate();
    
    assertThat(this.member.isActive()).isTrue();
    
    member.deactivate();
    
    assertThat(this.member.isActive()).isFalse();
  }
  
  @Test
  void invalidEmail() {
    assertThatThrownBy(() -> Member.register(MemberRegisterRequest.of("invalid-email", "nickname", "password"), passwordEncoder))
            .isInstanceOf(IllegalArgumentException.class);

    Member.register(MemberRegisterRequest.of("orolsyeo@gmail.com", "nickname", "password"), passwordEncoder);

  }
  
}