package hongik.cartoonforblindbackend.domain.user.dto;


import hongik.cartoonforblindbackend.domain.user.entity.User;
import hongik.cartoonforblindbackend.domain.user.entity.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;


public record SignupRequestDto(

//      @NotBlank(message = "username 공백일 수 없습니다.")
//      @Pattern(regexp ="^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "이메일 형식에 맞게 적어 주세요")

    String email,

    //나중에 비번 정규식 추가
//      @NotBlank(message = "비밀번호 공백일 수 없습니다.")
    String password,

//      @NotBlank(message = "비밀번호 확인 공백일 수 없습니다.")

    boolean isAdmin,
    String adminToken) {

  public User toEntity(String passwordEncoder, UserRoleEnum role) {
    return User.builder()
        .password(passwordEncoder)
        .createdAt(new Date())
        .updatedAt(new Date())
        .email(email)
        .role(role)
        .build();
  }


}
