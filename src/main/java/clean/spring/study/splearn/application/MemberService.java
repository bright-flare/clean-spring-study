package clean.spring.study.splearn.application;

import clean.spring.study.splearn.application.provided.MemberRegister;
import clean.spring.study.splearn.application.required.EmailSender;
import clean.spring.study.splearn.application.required.MemberRepository;
import clean.spring.study.splearn.domain.Member;
import clean.spring.study.splearn.domain.MemberRegisterRequest;
import clean.spring.study.splearn.domain.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {
  
  private final MemberRepository memberRepository;
  private final EmailSender emailSender;
  private final PasswordEncoder passwordEncoder;
  
  @Override
  public Member register(MemberRegisterRequest registerRequest) {

    Member member = Member.register(registerRequest, passwordEncoder);
    
    memberRepository.save(member);
    
    emailSender.send(member.getEmail(), "회원 가입을 환영합니다!", "아래 링크를 클릭해서 등록을 완료해주세요 ~!");
    
    return member;
    
  }
  
}
