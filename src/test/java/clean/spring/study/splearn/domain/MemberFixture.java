package clean.spring.study.splearn.domain;

public class MemberFixture {
 
  public static MemberRegisterRequest createMemberRegister() {
    return MemberRegisterRequest.of("bright-flare@splearn.app", "bright-flare", "password");
  }

  public static MemberRegisterRequest createMemberRegister(String invalidEmail) {
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
  
}
