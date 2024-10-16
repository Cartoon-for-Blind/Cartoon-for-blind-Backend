package hongik.cartoonforblindbackend.domain.panel.controller;


import hongik.cartoonforblindbackend.domain.panel.dto.PanelRequestDto;
import hongik.cartoonforblindbackend.domain.panel.service.PanelService;
import hongik.cartoonforblindbackend.global.response.ApiResponse;
import hongik.cartoonforblindbackend.global.security.userDetails.UserDetailsImpl;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/panel")
@RequiredArgsConstructor
public class PanelController {

  private final PanelService panelService;

  @PostMapping("/create/{bookId}/{pageId}")
  public ResponseEntity<?> createPanel(@AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestPart PanelRequestDto panelRequestDto, @RequestPart
  MultipartFile panelImage, @PathVariable Long bookId, @PathVariable Long pageId)
      throws IOException {
    // 컷 이미지를 S3에 저장
    String imageUrl = panelService.savePanelImage(panelImage,userDetails.getUsername(), bookId, pageId);

    // 이미지 URL을 DTO에 설정
    panelRequestDto.setPanelImageUrl(imageUrl);

    // 컷 생성 로직 호출
    panelService.createPanel(userDetails.getUser(), panelRequestDto,pageId);

    return ResponseEntity.ok(ApiResponse.ok("Panel 생성 완료", null));
  }

  @DeleteMapping("/delete/{panelId}")
  public ResponseEntity<?> deletePanel(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long panelId){
    panelService.deletePanel(userDetails.getUser(),panelId);
    return ResponseEntity.ok(ApiResponse.ok("Panel 삭제 완료", null));
  }
}
