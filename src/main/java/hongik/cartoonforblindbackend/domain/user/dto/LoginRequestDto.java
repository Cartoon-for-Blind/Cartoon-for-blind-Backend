package hongik.cartoonforblindbackend.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
    String email,
    String password){

}
