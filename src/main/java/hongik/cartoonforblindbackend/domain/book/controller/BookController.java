package hongik.cartoonforblindbackend.domain.book.controller;

import hongik.cartoonforblindbackend.domain.book.dto.BookCreateRequestDto;
import hongik.cartoonforblindbackend.domain.book.dto.BookResponseDto;
import hongik.cartoonforblindbackend.domain.book.dto.SearchBookResponseDto;
import hongik.cartoonforblindbackend.domain.book.entity.Book;
import hongik.cartoonforblindbackend.domain.book.service.BookService;
import hongik.cartoonforblindbackend.domain.user.entity.User;
import hongik.cartoonforblindbackend.domain.user.repository.UserRepository;
import hongik.cartoonforblindbackend.global.exception.BusinessException;
import hongik.cartoonforblindbackend.global.exception.ErrorCode;
import hongik.cartoonforblindbackend.global.response.ApiResponse;
import hongik.cartoonforblindbackend.global.security.userDetails.UserDetailsImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

  private final BookService bookService;
  private final UserRepository userRepository;

  @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> createBook(
      @RequestPart("bookInfo") BookCreateRequestDto dto,@RequestPart("bookCoverImage") MultipartFile file) throws IOException {
    Long userId = 1L;
    User user = userRepository.findByUserId(userId)
        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND,
            ErrorCode.AUTHENTICATION_EXCEPTION));


    // username과 bookName 가져오기
    String username = user.getUsername();
    String title = dto.getTitle();

    // 책 커버 이미지를 S3에 저장
    String imageUrl = bookService.saveBookCover(file, username, title);

    // 이미지 URL을 DTO에 설정
    dto.setCoverImageUrl(imageUrl);

    // 책 생성 로직 호출
    bookService.createBook(user, dto);

    return ResponseEntity.ok(ApiResponse.ok("책 생성 완료", null));
  }

  @GetMapping("/search/{bookId}")
  public ResponseEntity<?> searchBook(@PathVariable Long bookId) {
    Long userId = 1L;
    User user = userRepository.findByUserId(userId)
        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND,
            ErrorCode.AUTHENTICATION_EXCEPTION));

    Book book = bookService.searchBook(user, bookId);
    String signedUrl = bookService.generateSignedUrl(book.getUser().getUsername(),book.getTitle());
    BookResponseDto bookResponseDto = new BookResponseDto(bookId, signedUrl,
        book.getTitle(), book.getAuthor(), book.getPages());
    return ResponseEntity.ok(ApiResponse.ok("책 검색 완료", bookResponseDto));
  }



  @GetMapping("/search")
  public ResponseEntity<?> searchBooks() {
    Long userId = 1L;
    User user = userRepository.findByUserId(userId)
        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND,
            ErrorCode.AUTHENTICATION_EXCEPTION));

    List<Book> bookList = bookService.searchBooks(user);
    List<SearchBookResponseDto> searchBookResponseDtos = new ArrayList<>();
    for (Book book : bookList) {
      SearchBookResponseDto searchBookResponseDto = new SearchBookResponseDto(book);
      searchBookResponseDtos.add(searchBookResponseDto);
    }
    System.out.println("bookList = " + bookList);
    return ResponseEntity.ok(ApiResponse.ok("책들 검색 완료", searchBookResponseDtos));
  }

  @DeleteMapping("/delete/{bookId}")
  public ResponseEntity<?> deleteBooks(@AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long bookId) {
    bookService.deleteBook(userDetails.getUser(), bookId);
    return ResponseEntity.ok(ApiResponse.ok("책 삭제 완료", null));
  }


}
