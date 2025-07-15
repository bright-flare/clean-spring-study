package clean.spring.study.splearn.domain;

public record MemberCreateRequest(
        String email, 
        String nickname, 
        String password) {
  
  static MemberCreateRequest of(String email, String nickname, String password) {
    return new MemberCreateRequest(email, nickname, password);
  }
  
}
