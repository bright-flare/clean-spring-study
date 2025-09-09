package clean.spring.study.splearn.application.member;

import clean.spring.study.splearn.application.member.provided.MemberFinder;
import clean.spring.study.splearn.application.member.provided.MemberRegister;
import clean.spring.study.splearn.application.member.required.EmailSender;
import clean.spring.study.splearn.application.member.required.MemberRepository;
import clean.spring.study.splearn.domain.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {

  private final MemberFinder memberFinder;
  private final MemberRepository memberRepository;
  private final EmailSender emailSender;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Member register(MemberRegisterRequest registerRequest) {
    
    checkDuplicateEmail(registerRequest);

    Member member = Member.register(registerRequest, passwordEncoder);

    memberRepository.save(member);

    sendWelcomeEmail(member);

    return member;

  }

  @Override
  public Member activate(Long memberId) {
    
    Member member = memberFinder.find(memberId);
    member.activate();

    return memberRepository.save(member); // 현재 spring data 진영에서는 update를 위하여 save를 명시적으로 호출하는 것을 권유함.
  }

  private void sendWelcomeEmail(Member member) {
    emailSender.send(member.getEmail(), "회원 가입을 환영합니다!", "아래 링크를 클릭해서 등록을 완료해주세요 ~!");
  }

  private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
    if (memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
      throw new DuplicateEmailException("이미 사용중인 이메일입니다: " + registerRequest.email());
    }
  }

}
