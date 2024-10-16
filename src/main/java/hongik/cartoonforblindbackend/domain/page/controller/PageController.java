package hongik.cartoonforblindbackend.domain.page.controller;

import hongik.cartoonforblindbackend.domain.book.service.BookService;
import hongik.cartoonforblindbackend.domain.page.dto.PageRequestDto;
import hongik.cartoonforblindbackend.domain.page.entity.Page;
import hongik.cartoonforblindbackend.domain.page.service.PageService;
import hongik.cartoonforblindbackend.global.response.ApiResponse;
import hongik.cartoonforblindbackend.global.security.userDetails.UserDetailsImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/page")
public class PageController {

  private final PageService pageService;

  @PostMapping("/create/{bookId}")
  public ResponseEntity<?> createPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long bookId) {
    pageService.createPage(userDetails.getUser(), bookId);
    return ResponseEntity.ok(ApiResponse.ok("Page 생성 성공", null));
  }

  @GetMapping("/search/{bookId}/{pageNumber}")
  public ResponseEntity<?> searchPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long bookId, @PathVariable Long pageNumber) {

    List<Page> pageList = pageService.searchPage(userDetails.getUser(), bookId, pageNumber);
    return ResponseEntity.ok(ApiResponse.ok("페이지 검색 성공", pageList));
  }

  @DeleteMapping("delete/{pageId}")
  public ResponseEntity<?> deletePage(@AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long pageId) {
    pageService.deletePage(pageId);
    return ResponseEntity.ok(ApiResponse.ok("페이지 삭제 성공", null));
  }


}
