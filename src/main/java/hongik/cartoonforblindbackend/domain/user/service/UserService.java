package hongik.cartoonforblindbackend.domain.user.service;

import hongik.cartoonforblindbackend.domain.user.dto.LoginRequestDto;
import hongik.cartoonforblindbackend.domain.user.dto.SignupRequestDto;
import hongik.cartoonforblindbackend.domain.user.dto.TokenDto;
import hongik.cartoonforblindbackend.domain.user.entity.User;
import hongik.cartoonforblindbackend.domain.user.entity.UserRoleEnum;
import hongik.cartoonforblindbackend.domain.user.repository.UserRepository;
import hongik.cartoonforblindbackend.global.exception.BusinessException;
import hongik.cartoonforblindbackend.global.exception.ErrorCode;
import hongik.cartoonforblindbackend.global.security.userDetails.UserDetailsImpl;
import hongik.cartoonforblindbackend.global.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtUtil jwtUtil;
  public void signup(SignupRequestDto requestDto) {
    UserRoleEnum role = UserRoleEnum.USER;

    User user = requestDto.toEntity(passwordEncoder.encode(requestDto.password()), role);

    userRepository.save(user);
  }

  public TokenDto login(LoginRequestDto requestDto) {
    String email = requestDto.email();
    String password = requestDto.password();

    User user = userRepository.findByEmail(email).orElseThrow(
        () -> new BusinessException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_FOUND_USER_EXCEPTION));

    Authentication authentication = createAuthentication(password, user);
    setAuthentication(authentication);

    TokenDto tokenDto = TokenDto.of(jwtUtil.createAccessToken(email, user.getRole()),
        jwtUtil.createRefreshToken(email, user.getRole()));

    return tokenDto;
  }


  private Authentication createAuthentication(String password, User user) {
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new BusinessException(HttpStatus.BAD_REQUEST,
          ErrorCode.FAILED_AUTHENTICATION_EXCEPTION);
    }

    UserDetailsImpl userDetails = new UserDetailsImpl(user);
    return new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(),
        userDetails.getAuthorities());
  }

  private void setAuthentication(Authentication authentication) {
    SecurityContext context = SecurityContextHolder.getContext();
    context.setAuthentication(authentication);
  }
}
