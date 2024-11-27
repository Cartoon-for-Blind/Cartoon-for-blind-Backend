package hongik.cartoonforblindbackend.domain.panel.controller;


import hongik.cartoonforblindbackend.domain.panel.dto.PanelRequestDto;
import hongik.cartoonforblindbackend.domain.panel.service.PanelService;
import hongik.cartoonforblindbackend.domain.user.entity.User;
import hongik.cartoonforblindbackend.domain.user.repository.UserRepository;
import hongik.cartoonforblindbackend.global.exception.BusinessException;
import hongik.cartoonforblindbackend.global.exception.ErrorCode;
import hongik.cartoonforblindbackend.global.response.ApiResponse;
import hongik.cartoonforblindbackend.global.security.userDetails.UserDetailsImpl;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/panel")
@RequiredArgsConstructor
public class PanelController {

  private final PanelService panelService;
  private final UserRepository userRepository;

  @PostMapping("/create/{pageId}")
  public ResponseEntity<?> createPanel(
      @RequestBody PanelRequestDto panelRequestDto,
      @PathVariable Long pageId)
      throws IOException {
    Long userId = 1L;
    User user = userRepository.findByUserId(userId).orElseThrow(() -> new BusinessException(
        HttpStatus.NOT_FOUND,
        ErrorCode.AUTHENTICATION_EXCEPTION));



    panelService.createPanel(user, panelRequestDto, pageId);

    return ResponseEntity.ok(ApiResponse.ok("Panel 생성 완료", null));
  }

  @DeleteMapping("/delete/{panelId}")
  public ResponseEntity<?> deletePanel(@AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long panelId) {
    panelService.deletePanel(userDetails.getUser(), panelId);
    return ResponseEntity.ok(ApiResponse.ok("Panel 삭제 완료", null));
  }
}
