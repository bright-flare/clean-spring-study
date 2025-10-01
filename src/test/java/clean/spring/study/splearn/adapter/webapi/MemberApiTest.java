package clean.spring.study.splearn.adapter.webapi;

import clean.spring.study.splearn.application.member.provided.MemberRegister;
import clean.spring.study.splearn.domain.member.Member;
import clean.spring.study.splearn.domain.member.MemberFixture;
import clean.spring.study.splearn.domain.member.MemberRegisterRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(MemberApi.class)
class MemberApiTest {

  @MockitoBean
  private MemberRegister memberRegister;
  
  @Autowired
  MockMvcTester mockMvcTester;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void register() throws Exception {

    Member member = MemberFixture.createMember(1L);
    when(memberRegister.register(any())).thenReturn(member);

    MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
    String requestJson = objectMapper.writeValueAsString(request);

    assertThat(mockMvcTester.post().uri("/members").contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .hasStatusOk()
            .bodyJson()
            .extractingPath("$.memberId").asNumber().isEqualTo(1);
    
  }
  
}