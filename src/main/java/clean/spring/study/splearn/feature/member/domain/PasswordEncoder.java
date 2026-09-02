package clean.spring.study.splearn.feature.member.domain;

public interface PasswordEncoder {
  
  String encode(String password);
  
  boolean matches(String password, String passwordHash);
  
}
