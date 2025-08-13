package clean.spring.study.splearn.application.provided;

import clean.spring.study.splearn.application.MemberService;
import clean.spring.study.splearn.application.required.EmailSender;
import clean.spring.study.splearn.application.required.MemberRepository;
import clean.spring.study.splearn.domain.Email;
import clean.spring.study.splearn.domain.Member;
import clean.spring.study.splearn.domain.MemberFixture;
import clean.spring.study.splearn.domain.MemberStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class MemberRegisterManualTest {

  @Test
  void registerTestStub() {

    MemberRegister register = new MemberService(
            new MemberRepositoryStub(),
            new EmailSenderStub(),
            MemberFixture.createPasswordEncoder()
    );

    Member member = register.register(MemberFixture.createMemberRegisterRequest());

    assertThat(member.getId()).isNotNull();
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    
  }
  
  @Test
  void registerTestMock() {
    
    EmailSenderMock emailSenderMock = new EmailSenderMock();

    MemberRegister register = new MemberService(
            new MemberRepositoryStub(),
            emailSenderMock,
            MemberFixture.createPasswordEncoder()
    );

    Member member = register.register(MemberFixture.createMemberRegisterRequest());

    assertThat(member.getId()).isNotNull();
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    
    assertThat(emailSenderMock.getTos()).hasSize(1);
    assertThat(emailSenderMock.getTos().getFirst()).isEqualTo(member.getEmail());
    
  }
  
  @Test
  void registerTestMockito() {
    
    EmailSender emailSender = Mockito.mock(EmailSender.class);

    MemberRegister register = new MemberService(
            new MemberRepositoryStub(),
            emailSender,
            MemberFixture.createPasswordEncoder()
    );

    Member member = register.register(MemberFixture.createMemberRegisterRequest());

    assertThat(member.getId()).isNotNull();
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    
    Mockito.verify(emailSender).send(eq(member.getEmail()), any(), any());
  }

  static class MemberRepositoryStub implements MemberRepository {
    @Override
    public Member save(Member member) {
      
      ReflectionTestUtils.setField(member, "id", 1L);
      
      return member;
    }

    @Override
    public Optional<Member> findByEmail(Email email) {
      return Optional.empty();
    }

    @Override
    public Optional<Member> findById(Long memberId) {
      return Optional.empty();
    }
  }

  static class EmailSenderStub implements EmailSender {
    @Override
    public void send(Email email, String subject, String content) {
      
    }
  }
  
  static class EmailSenderMock implements EmailSender {
    
    List<Email> to = new ArrayList<>();
    
    @Override
    public void send(Email email, String subject, String content) {
      to.add(email);
    }
    
    public List<Email> getTos() {
      return to;
    }
  }
}