package clean.spring.study.splearn.feature.member.adapter.webapi;

import clean.spring.study.splearn.feature.member.adapter.webapi.dto.MemberRegisterResponse;
import clean.spring.study.splearn.feature.member.application.provided.MemberRegister;
import clean.spring.study.splearn.feature.member.domain.Member;
import clean.spring.study.splearn.feature.member.application.dto.MemberRegisterRequest;
import clean.spring.study.splearn.support.stereotype.WebApiAdapter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebApiAdapter
@RequiredArgsConstructor
public class MemberApi {

  private final MemberRegister memberRegister;
  
  @PostMapping("/members")
  public MemberRegisterResponse register(@RequestBody @Valid MemberRegisterRequest request) {
    Member member = memberRegister.register(request);
    return MemberRegisterResponse.of(member);
  }
  
}
