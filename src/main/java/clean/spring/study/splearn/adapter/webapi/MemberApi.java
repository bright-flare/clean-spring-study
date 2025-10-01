package clean.spring.study.splearn.adapter.webapi;

import clean.spring.study.splearn.adapter.webapi.dto.MemberRegisterResponse;
import clean.spring.study.splearn.application.member.provided.MemberRegister;
import clean.spring.study.splearn.domain.member.Member;
import clean.spring.study.splearn.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApi {

  private final MemberRegister memberRegister;
  
  @PostMapping("/members")
  public MemberRegisterResponse register(@RequestBody @Valid MemberRegisterRequest request) {
    Member member = memberRegister.register(request);
    return MemberRegisterResponse.of(member);
  }
  
}
