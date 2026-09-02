package clean.spring.study.splearn.feature.member.application.required;


import clean.spring.study.splearn.feature.member.domain.Email;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.Profile;

import java.util.Optional;

/**
 * MemberRepository 인터페이스는 회원 도메인 객체를 저장하고 조회하는 메서드를 정의합니다.
 */
public interface MemberRepository {
  
  Member save(Member member);
  
  Optional<Member> findByEmail(Email email);

  Optional<Member> findById(Long memberId);

  Optional<Member> findByProfile(Profile profile);
  
}
