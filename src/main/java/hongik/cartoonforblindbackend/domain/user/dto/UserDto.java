package hongik.cartoonforblindbackend.domain.user.dto;

import hongik.cartoonforblindbackend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

  private Long userId;
  private String username;
  private String email;
  private String profileImage;

  static public UserDto toDto(User user) {
    return UserDto.builder()
        .userId(user.getUserId())
        .username(user.getUsername())
        .email(user.getEmail())
        .profileImage(user.getProfileImage()).build();
  }

  public User toEntity() {
    return User.builder()
        .userId(userId)
        .username(username)
        .email(email)
        .profileImage(profileImage).build();
  }
}