package clean.spring.study.splearn.domain.member;

import clean.spring.study.splearn.domain.shared.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MemberDetail extends AbstractEntity {

  private Profile profile;
  
  private String introduction;
  
  private LocalDateTime registeredAt;
  
  private LocalDateTime activatedAt;
  
  private LocalDateTime deactivatedAt;

  protected static MemberDetail create() {

    MemberDetail memberDetail = new MemberDetail();
    memberDetail.registeredAt = LocalDateTime.now();

    return memberDetail;
  }

  void updateActivatedAt() {
    Assert.isTrue(this.activatedAt == null, "activatedAt 은 이미 설정되어 있습니다.");
    this.activatedAt = LocalDateTime.now();
  }
  
  void updateDeactivatedAt() {
    Assert.isTrue(this.deactivatedAt == null, "deactivatedAt 은 이미 설정되어 있습니다.");
    this.deactivatedAt = LocalDateTime.now();
  }

  void updateInfo(MemberInfoUpdateRequest updateRequest) {
    this.profile = new Profile(updateRequest.profileAddress());
    this.introduction = Objects.requireNonNull(updateRequest.introduction());
  }
  
}
