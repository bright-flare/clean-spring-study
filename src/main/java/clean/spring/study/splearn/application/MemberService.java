package clean.spring.study.splearn.application;

import clean.spring.study.splearn.application.provided.MemberRegister;
import clean.spring.study.splearn.application.required.EmailSender;
import clean.spring.study.splearn.application.required.MemberRepository;
import clean.spring.study.splearn.domain.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberService implements MemberRegister {
  
  private final MemberRepository memberRepository;
  private final EmailSender emailSender;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Member register(@Valid MemberRegisterRequest registerRequest) {
    
    checkDuplicateEmail(registerRequest);

    Member member = Member.register(registerRequest, passwordEncoder);

    memberRepository.save(member);

    sendWelcomeEmail(member);

    return member;

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
