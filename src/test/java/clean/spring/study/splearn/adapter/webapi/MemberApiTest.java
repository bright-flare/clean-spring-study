package clean.spring.study.splearn.adapter.webapi;

import clean.spring.study.splearn.adapter.webapi.dto.MemberRegisterResponse;
import clean.spring.study.splearn.application.member.provided.MemberRegister;
import clean.spring.study.splearn.application.member.required.MemberRepository;
import clean.spring.study.splearn.domain.member.Member;
import clean.spring.study.splearn.domain.member.MemberFixture;
import clean.spring.study.splearn.domain.member.MemberRegisterRequest;
import clean.spring.study.splearn.domain.member.MemberStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.transaction.annotation.Transactional;

import static clean.spring.study.splearn.AssertThatUtils.equalsTo;
import static clean.spring.study.splearn.AssertThatUtils.notNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Transactional
public class MemberApiTest {
  
  final MockMvcTester mvcTester;
  final ObjectMapper objectMapper;
  final MemberRepository memberRepository;
  final MemberRegister memberRegister;
  
  @Test
  void register() throws Exception {
    
    MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
    String requestJson = objectMapper.writeValueAsString(request);

    MvcTestResult result = mvcTester.post().uri("/members").contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
            .exchange();

    assertThat(result)
            .hasStatusOk()
            .bodyJson()
            .hasPathSatisfying("$.memberId", notNull())
            .hasPathSatisfying("$.email", equalsTo(request.email()));

    String content = result.getResponse().getContentAsString();
    MemberRegisterResponse response = objectMapper.readValue(content, MemberRegisterResponse.class);

    Member member = memberRepository.findById(response.memberId()).orElseThrow();
    
    assertThat(member.getEmail().email()).isEqualTo(response.email());
    assertThat(member.getNickname()).isEqualTo(request.nickname());
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    
  }

  @Test
  void duplicateEmail() throws JsonProcessingException {
    
    Member existingMember = memberRegister.register(MemberFixture.createMemberRegisterRequest());

    MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest(existingMember.getEmail().email());
    String requestJson = objectMapper.writeValueAsString(request);

    MvcTestResult result = mvcTester.post().uri("/members").contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
            .exchange();

    assertThat(result)
            .apply(print())
            .hasStatus(HttpStatus.CONFLICT)
    ;
    
  }
}
