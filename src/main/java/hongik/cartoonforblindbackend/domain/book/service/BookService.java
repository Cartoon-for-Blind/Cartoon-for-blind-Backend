package hongik.cartoonforblindbackend.domain.book.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import hongik.cartoonforblindbackend.domain.book.dto.BookCreateRequestDto;
import hongik.cartoonforblindbackend.domain.book.entity.Book;
import hongik.cartoonforblindbackend.domain.book.repository.BookRepository;
import hongik.cartoonforblindbackend.domain.user.entity.User;
import hongik.cartoonforblindbackend.domain.user.entity.UserRoleEnum;
import hongik.cartoonforblindbackend.domain.user.repository.UserRepository;
import hongik.cartoonforblindbackend.global.exception.BusinessException;
import hongik.cartoonforblindbackend.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  @Autowired
  private AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  public void createBook(User user, BookCreateRequestDto dto) {
    Book book = new Book(dto, user);
    bookRepository.save(book);
  }

  public String saveBookCover(MultipartFile file,String username, String title) throws IOException {
    // 경로 설정: username/title/coverImage
    String directoryStructure = username + "/" + title + "/";
    String fileName = directoryStructure + "coverImage";

    ObjectMetadata metadata= new ObjectMetadata();
    metadata.setContentType(file.getContentType());
    metadata.setContentLength(file.getSize());

    // S3에 파일 업로드
    amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(),metadata));

    // S3에서 파일의 URL 생성
    return amazonS3.getUrl(bucketName, fileName).toString();
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
