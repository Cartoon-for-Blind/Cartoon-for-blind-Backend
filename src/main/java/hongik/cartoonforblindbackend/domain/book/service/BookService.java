package hongik.cartoonforblindbackend.domain.book.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
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

  public String saveBookCover(MultipartFile file, String username, String title) throws IOException {
    // 경로 설정: username/title/coverImage
    String directoryStructure = username + "/" + title + "/";
    String fileName = directoryStructure + "coverImage";

    // MultipartFile의 InputStream을 사용하여 파일 업로드
    try (InputStream inputStream = file.getInputStream()) {
      // 파일의 메타데이터 설정
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(file.getContentType());  // 파일 MIME 타입 설정
      metadata.setContentLength(file.getSize());  // 파일 크기 설정

      // 파일을 S3에 업로드
      amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
    }

    // S3에서 파일의 URL 생성
    return amazonS3.getUrl(bucketName, fileName).toString();
  }


  public String generateSignedUrl(String username, String title) {
    // S3에 저장된 파일 경로 설정
    String directoryStructure = username + "/" + title + "/";
    String fileName = directoryStructure + "coverImage";

    // URL의 유효 기간 설정 (예: 1시간)
    Date expiration = new Date();
    long expTimeMillis = expiration.getTime();
    expTimeMillis += 1000 * 60 * 60; // 1시간
    expiration.setTime(expTimeMillis);

    // Signed URL 생성 요청 설정
    GeneratePresignedUrlRequest generatePresignedUrlRequest =
        new GeneratePresignedUrlRequest(bucketName, fileName)
            .withMethod(HttpMethod.GET)
            .withExpiration(expiration);

    // Signed URL 생성
    URL signedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
    return signedUrl.toString();
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
