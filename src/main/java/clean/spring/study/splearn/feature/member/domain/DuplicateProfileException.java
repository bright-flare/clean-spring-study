package clean.spring.study.splearn.feature.member.domain;

public class DuplicateProfileException extends RuntimeException {

  public DuplicateProfileException(String profileAddress) {
    super(profileAddress);
  }

}
