package clean.spring.study.splearn.adapter.webapi.dto;

import clean.spring.study.splearn.domain.member.Member;

public record MemberRegisterResponse(Long memberId, String emailAddress) {
  
  public static MemberRegisterResponse of(Member member) {
    return new MemberRegisterResponse(member.getId(), member.getEmail().email());
  }
  
}
