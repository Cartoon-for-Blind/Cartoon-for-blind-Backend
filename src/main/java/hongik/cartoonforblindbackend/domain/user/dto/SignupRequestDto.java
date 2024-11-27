package hongik.cartoonforblindbackend.domain.user.dto;


import hongik.cartoonforblindbackend.domain.user.entity.User;
import hongik.cartoonforblindbackend.domain.user.entity.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;


public record SignupRequestDto(

    String email,
    String password,
    String username,

    boolean isAdmin,
    String adminToken) {

  public User toEntity(String passwordEncoder, UserRoleEnum role) {
    return User.builder()
        .username(username)
        .password(passwordEncoder)
        .createdAt(new Date())
        .updatedAt(new Date())
        .email(email)
        .role(role)
        .build();
  }


}
