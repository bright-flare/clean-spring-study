package clean.spring.study.splearn.feature.member.application;

import clean.spring.study.splearn.feature.member.application.provided.MemberFinder;
import clean.spring.study.splearn.feature.member.application.required.MemberRepository;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.support.stereotype.ApplicationService;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {
  
  private final MemberRepository memberRepository;
  
  @Override
  public Member find(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다: " + memberId));
  }
  
}
