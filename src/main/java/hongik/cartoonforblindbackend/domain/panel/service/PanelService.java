package hongik.cartoonforblindbackend.domain.panel.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import hongik.cartoonforblindbackend.domain.book.entity.Book;
import hongik.cartoonforblindbackend.domain.book.repository.BookRepository;
import hongik.cartoonforblindbackend.domain.page.dto.PageRequestDto;
import hongik.cartoonforblindbackend.domain.page.entity.Page;
import hongik.cartoonforblindbackend.domain.page.repository.PageRepository;
import hongik.cartoonforblindbackend.domain.panel.dto.PanelRequestDto;
import hongik.cartoonforblindbackend.domain.panel.entity.Panel;
import hongik.cartoonforblindbackend.domain.panel.repository.PanelRepository;
import hongik.cartoonforblindbackend.domain.user.entity.User;
import hongik.cartoonforblindbackend.global.exception.BusinessException;
import hongik.cartoonforblindbackend.global.exception.ErrorCode;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PanelService {

  @Autowired
  private AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  private final BookRepository bookRepository;
  private final PanelRepository panelRepository;
  private final PageRepository pageRepository;

  public String savePanelImage(MultipartFile panelImage, String username, Long bookId, Long pageId)
      throws IOException {

    Book book = bookRepository.findByBookId(bookId).orElseThrow(() -> new BusinessException(
        HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_BOOK));

    // 경로 설정: username/bookId/pageId
    String directoryStructure = username + "/" + book.getTitle() + "/" + pageId + "/";
    String fileName = directoryStructure + pageId;

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(panelImage.getContentType());
    metadata.setContentLength(panelImage.getSize());

    // S3에 파일 업로드
    amazonS3.putObject(
        new PutObjectRequest(bucketName, fileName, panelImage.getInputStream(), metadata));

    // S3에서 파일의 URL 생성
    return amazonS3.getUrl(bucketName, fileName).toString();
  }

  public void createPanel(User user, PanelRequestDto panelRequestDto, Long pageId) {
    Page page = pageRepository.findByPageId(pageId)
        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_PAGE));
    Panel panel = new Panel(panelRequestDto, page);
    panelRepository.save(panel);
  }

  public void deletePanel(User user, Long panelId) {
    Panel panel = panelRepository.findByPanelId(panelId)
        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_PANEL));
    panelRepository.delete(panel);
  }
}
