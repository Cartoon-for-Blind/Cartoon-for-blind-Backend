package hongik.cartoonforblindbackend.domain.page.service;


import hongik.cartoonforblindbackend.domain.book.entity.Book;
import hongik.cartoonforblindbackend.domain.book.repository.BookRepository;
import hongik.cartoonforblindbackend.domain.page.entity.Page;
import hongik.cartoonforblindbackend.domain.page.repository.PageRepository;
import hongik.cartoonforblindbackend.domain.user.entity.User;
import hongik.cartoonforblindbackend.global.exception.BusinessException;
import hongik.cartoonforblindbackend.global.exception.ErrorCode;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PageService {

  private final PageRepository pageRepository;
  private final BookRepository bookRepository;

  public List<Page> searchPage(User user, Long bookId, Long pageNumber) {
    pageRepository.findByBook_BookIdAndPageNumber(bookId, pageNumber);
    return null;
  }

  public void createPage(User user,Long bookId) {
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new RuntimeException("Book not found"));

    // 현재 페이지 수 조회
    int currentPageCount = pageRepository.countByBook(book);

    // 새로운 페이지 번호 설정
    int newPageNumber = currentPageCount + 1;

    // 새로운 페이지 생성
    Page newPage = Page.builder()
        .pageNumber(newPageNumber)
        .book(book)
        .createdAt(new Date())
        .updatedAt(new Date())
        .build();

    pageRepository.save(newPage);
  }

  public void deletePage(Long pageId) {
    Page page = pageRepository.findById(pageId).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND,
        ErrorCode.NOT_FOUND_PAGE));
    pageRepository.delete(page);
  }
}
