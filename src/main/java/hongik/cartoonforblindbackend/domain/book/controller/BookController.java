package hongik.cartoonforblindbackend.domain.book.controller;

import hongik.cartoonforblindbackend.domain.book.dto.BookCreateRequestDto;
import hongik.cartoonforblindbackend.domain.book.service.BookService;
import hongik.cartoonforblindbackend.domain.book.entity.Book;
import hongik.cartoonforblindbackend.global.response.ApiResponse;
import hongik.cartoonforblindbackend.global.security.userDetails.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
private final BookService bookService;
  @PostMapping("/create")
  public ResponseEntity<?> createBook(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody
      BookCreateRequestDto dto){
    bookService.createBook(userDetails.getUser(),dto);
    return ResponseEntity.ok(ApiResponse.ok("책 생성 완료",null));
  }

  @GetMapping("/search/{bookId}")
  public ResponseEntity<?> searchBook(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long bookId){
    Book book = bookService.searchBook(userDetails.getUser(),bookId);
    return ResponseEntity.ok(ApiResponse.ok("책 검색 완료", book));
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchBooks(@AuthenticationPrincipal UserDetailsImpl userDetails){
    List<Book> bookList = bookService.searchBooks(userDetails.getUser());
    return ResponseEntity.ok(ApiResponse.ok("책들 검색 완료", bookList));
  }

  @DeleteMapping("/delete/{bookId}")
  public ResponseEntity<?> deleteBooks(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long bookId){
    bookService.deleteBook(userDetails.getUser(),bookId);
    return ResponseEntity.ok(ApiResponse.ok("책 삭제 완료", null));
  }

}
