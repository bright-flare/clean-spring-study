package clean.spring.study.splearn.application.member.required;


import clean.spring.study.splearn.domain.member.Email;
import clean.spring.study.splearn.domain.member.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * MemberRepository 인터페이스는 회원 도메인 객체를 저장하고 조회하는 메서드를 정의합니다.
 * 이 인터페이스는 Spring Data JPA의 Repository를 확장하여 기본 CRUD 기능을 제공합니다.
 */
public interface MemberRepository extends Repository<Member,Long> {
  
  Member save(Member member);
  
  Optional<Member> findByEmail(Email email);

  Optional<Member> findById(Long memberId);
  
}
