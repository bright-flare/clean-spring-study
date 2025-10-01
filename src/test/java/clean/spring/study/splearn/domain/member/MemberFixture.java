package clean.spring.study.splearn.domain.member;

import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixture {
 
  public static MemberRegisterRequest createMemberRegisterRequest() {
    return MemberRegisterRequest.of("bright-flare@splearn.app", "bright-flare", "password");
  }

  public static MemberRegisterRequest createMemberRegisterRequest(String invalidEmail) {
    return MemberRegisterRequest.of(invalidEmail, "nickname", "password");
  }
  
  public static PasswordEncoder createPasswordEncoder() {
    return new PasswordEncoder() {
      @Override
      public String encode(String password) {
        return password.toUpperCase(); // For testing, we just return the password as is.
      }

      @Override
      public boolean matches(String password, String passwordHash) {
        return encode(password).equals(passwordHash);
      }
    };
  }

  public static Member createMember() {
    return Member.register(createMemberRegisterRequest(), createPasswordEncoder());
  }
  
  public static Member createMember(Long id) {
    Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
    ReflectionTestUtils.setField(member, "id", id);
    return member;
  }
  
}
