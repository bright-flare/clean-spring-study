package clean.spring.study.splearn.application.required;


import clean.spring.study.splearn.domain.Member;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member,Long> {
  
  Member save(Member member);
  
  Member findById(Long id);
  
}
