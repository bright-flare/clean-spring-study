package clean.spring.study.splearn.feature.member.domain;

import clean.spring.study.splearn.feature.shared.domain.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "member_detail", uniqueConstraints = {
    @UniqueConstraint(name = "uk_member_detail_profile_address", columnNames = "profile_address")
})
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MemberDetail extends AbstractEntity {

  @Embedded
  private Profile profile;

  @Column(columnDefinition = "TEXT")
  private String introduction;

  @Column(nullable = false)
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
    this.profile = convertToProfile(updateRequest.profileAddress());

    this.introduction = Objects.requireNonNull(updateRequest.introduction());
  }

    private Profile convertToProfile(@NotNull @Size(max = 15) String profileAddress) {
      if (profileAddress != null && profileAddress.isEmpty()) {
        return null;
      }
      return new Profile(profileAddress);
    }

}
