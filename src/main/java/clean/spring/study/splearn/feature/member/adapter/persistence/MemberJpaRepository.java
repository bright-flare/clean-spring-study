package clean.spring.study.splearn.feature.member.adapter.persistence;

import clean.spring.study.splearn.feature.member.domain.Email;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.detail.profile = :profile")
    Optional<Member> findByProfile(Profile profile);

    Optional<Member> findByEmail(Email email);

}
