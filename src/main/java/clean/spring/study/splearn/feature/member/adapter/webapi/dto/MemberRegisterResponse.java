package clean.spring.study.splearn.feature.member.adapter.webapi.dto;

import clean.spring.study.splearn.feature.member.domain.Member;

public record MemberRegisterResponse(Long memberId, String email) {
  
  public static MemberRegisterResponse of(Member member) {
    return new MemberRegisterResponse(member.getId(), member.getEmail().email());
  }
  
}
