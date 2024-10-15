package hongik.cartoonforblindbackend.domain.book.service;

import hongik.cartoonforblindbackend.domain.book.dto.BookCreateRequestDto;
import hongik.cartoonforblindbackend.domain.book.entity.Book;
import hongik.cartoonforblindbackend.domain.book.repository.BookRepository;
import hongik.cartoonforblindbackend.domain.user.entity.User;
import hongik.cartoonforblindbackend.domain.user.entity.UserRoleEnum;
import hongik.cartoonforblindbackend.domain.user.repository.UserRepository;
import hongik.cartoonforblindbackend.global.exception.BusinessException;
import hongik.cartoonforblindbackend.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  public void createBook(User user, BookCreateRequestDto dto) {
    Book book = new Book(dto, user);
    bookRepository.save(book);
  }

  public Book searchBook(User user, Long bookId) {
    Book book = bookRepository.findByBookId(bookId).orElseThrow(() -> new BusinessException(
        HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_BOOK));

    return book;
  }

  public List<Book> searchBooks(User user) {
    List<Book> bookList = userRepository.findByUserId(user.getUserId()).orElseThrow(
        () -> new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_EXCEPTION)).getBooks();
    return bookList;
  }

  public void deleteBook(User user, Long bookId) {
    Book book = bookRepository.findByBookId(bookId)
        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_BOOK));
    bookRepository.delete(book);
  }
}
