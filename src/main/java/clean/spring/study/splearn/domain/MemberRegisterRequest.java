package clean.spring.study.splearn.domain;

public record MemberRegisterRequest(
        String email, 
        String nickname, 
        String password) {
  
  static MemberRegisterRequest of(String email, String nickname, String password) {
    return new MemberRegisterRequest(email, nickname, password);
  }
  
}
