package clean.spring.study.splearn.domain.member;

public class DuplicateEmailException extends RuntimeException {

  public DuplicateEmailException(String email) {
    super(email);
  }

}
