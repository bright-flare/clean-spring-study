package clean.spring.study.splearn.feature.member.application.provided;

import clean.spring.study.splearn.feature.member.application.dto.MemberRegisterRequest;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.MemberInfoUpdateRequest;
import jakarta.validation.Valid;

/**
 * 회원의 등록과 관련된 기능을 제공하는 인터페이스입니다.
 * 
 */
public interface MemberRegister {
  
  Member register(@Valid MemberRegisterRequest registerRequest);
  
  Member activate(Long memberId);
  
  Member deactivate(Long memberId);

  Member updateInfo(Long id, @Valid MemberInfoUpdateRequest memberInfoUpdateRequest);
  
}
