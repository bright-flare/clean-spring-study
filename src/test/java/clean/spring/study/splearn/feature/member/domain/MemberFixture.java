package clean.spring.study.splearn.feature.member.domain;

import clean.spring.study.splearn.feature.member.application.dto.MemberRegisterRequest;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.PasswordEncoder;
import org.instancio.Instancio;
import org.springframework.test.util.ReflectionTestUtils;

import static org.instancio.Select.field;

public class MemberFixture {
 
  public static MemberRegisterRequest createMemberRegisterRequest() {
    return createMemberRegisterRequest(Instancio.gen().net().email().get());
  }

  public static MemberRegisterRequest createMemberRegisterRequest(String invalidEmail) {

    return Instancio.of(MemberRegisterRequest.class)
            .set(field(MemberRegisterRequest::email), invalidEmail)
            .create();

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
    return Member.register(createMemberRegisterRequest().toInfo(), createPasswordEncoder());
  }

  public static Member createActiveMember() {
    Member register = Member.register(createMemberRegisterRequest().toInfo(), createPasswordEncoder());
    register.activate();
    return register;
  }

  public static Member createMember(Long id) {
    Member member = Member.register(createMemberRegisterRequest().toInfo(), createPasswordEncoder());
    ReflectionTestUtils.setField(member, "id", id);
    return member;
  }
  
}
