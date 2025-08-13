package clean.spring.study.splearn.application.provided;

import clean.spring.study.splearn.domain.Member;

/**
 * MemberFinder 인터페이스는 회원 도메인 객체를 조회하는 기능을 제공합니다.
 */
public interface MemberFinder {
  Member find(Long memberId);
}
