package clean.spring.study.splearn.domain.member;

public class DuplicateProfileException extends RuntimeException {

  public DuplicateProfileException(String profileAddress) {
    super(profileAddress);
  }

}
