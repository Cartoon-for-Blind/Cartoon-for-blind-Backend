package hongik.cartoonforblindbackend.domain.user.controller;

import hongik.cartoonforblindbackend.domain.user.dto.LoginRequestDto;
import hongik.cartoonforblindbackend.domain.user.dto.SignupRequestDto;
import hongik.cartoonforblindbackend.domain.user.dto.TokenDto;
import hongik.cartoonforblindbackend.domain.user.dto.UserDto;
import hongik.cartoonforblindbackend.domain.user.service.UserService;
import hongik.cartoonforblindbackend.global.response.ApiResponse;
import hongik.cartoonforblindbackend.global.security.userDetails.UserDetailsImpl;
import hongik.cartoonforblindbackend.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;
  private final JwtUtil jwtUtil;

  // 회원가입
  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody SignupRequestDto requestDto) {
    userService.signup(requestDto);
    return ResponseEntity.ok(ApiResponse.ok("회원가입 성공.", null));
  }

  //로그인
  @PostMapping("/login")
  public ResponseEntity<?> login(
      @RequestBody LoginRequestDto requestDto,
      HttpServletResponse response) {

    TokenDto tokenDto = userService.login(requestDto);

    jwtUtil.setTokenResponse(tokenDto, response);

    return ResponseEntity.ok(ApiResponse.ok("로그인 성공.", tokenDto));
  }

  @PostMapping("/test")
  public ResponseEntity<?> test(HttpServletRequest request,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    UserDto dto = UserDto.toDto(userDetails.getUser());

    return ResponseEntity.ok(ApiResponse.ok("test", dto));
  }


}
