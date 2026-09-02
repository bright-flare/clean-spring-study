package clean.spring.study.splearn.feature.member.adapter.persistence;

import clean.spring.study.splearn.feature.member.application.required.MemberRepository;
import clean.spring.study.splearn.feature.member.domain.Email;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryAdapter implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public Optional<Member> findByEmail(Email email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return memberJpaRepository.findById(memberId);
    }

    @Override
    public Optional<Member> findByProfile(Profile profile) {
        return memberJpaRepository.findByProfile(profile);
    }
}
