package clean.spring.study.splearn.application;

import clean.spring.study.splearn.application.provided.MemberFinder;
import clean.spring.study.splearn.application.required.MemberRepository;
import clean.spring.study.splearn.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {
  
  private final MemberRepository memberRepository;
  
  @Override
  public Member find(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다: " + memberId));
  }
  
}
