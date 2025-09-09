package clean.spring.study.splearn.application.member.provided;

import clean.spring.study.splearn.domain.member.Member;
import clean.spring.study.splearn.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;

/**
 * 회원의 등록과 관련된 기능을 제공하는 인터페이스입니다.
 * 
 */
public interface MemberRegister {
  
  Member register(@Valid MemberRegisterRequest registerRequest);
  
  Member activate(Long memberId);
  
}
