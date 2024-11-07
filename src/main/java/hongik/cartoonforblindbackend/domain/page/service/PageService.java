package hongik.cartoonforblindbackend.domain.page.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hongik.cartoonforblindbackend.domain.book.entity.Book;
import hongik.cartoonforblindbackend.domain.book.repository.BookRepository;
import hongik.cartoonforblindbackend.domain.page.dto.UploadResponseDto;
import hongik.cartoonforblindbackend.domain.page.entity.Page;
import hongik.cartoonforblindbackend.domain.page.repository.PageRepository;
import hongik.cartoonforblindbackend.domain.panel.dto.PanelRequestDto;
import hongik.cartoonforblindbackend.domain.panel.service.PanelService;
import hongik.cartoonforblindbackend.domain.user.entity.User;
import hongik.cartoonforblindbackend.domain.user.repository.UserRepository;
import hongik.cartoonforblindbackend.global.exception.BusinessException;
import hongik.cartoonforblindbackend.global.exception.ErrorCode;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class PageService {

  private final PageRepository pageRepository;
  private final BookRepository bookRepository;
  private final UserRepository userRepository;
  private final PanelService panelService;

  private final RestTemplate restTemplate = new RestTemplate();
  private final String uploadUrl = "https://caring-poetic-reptile.ngrok-free.app/upload"; // 외부 API 엔드포인트


  public List<Page> searchPage(User user, Long bookId, Long pageNumber) {
    pageRepository.findByBook_BookIdAndPageNumber(bookId, pageNumber);
    return null;
  }

  public Long createPage(User user, Long bookId) {
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
    return newPage.getPageId();
  }

  public void deletePage(Long pageId) {
    Page page = pageRepository.findById(pageId)
        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND,
            ErrorCode.NOT_FOUND_PAGE));
    pageRepository.delete(page);
  }

  public void convertPage(Long bookId, MultipartFile file) {

    Long userId = 1L;
    User user = userRepository.findByUserId(userId)
        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND,
            ErrorCode.AUTHENTICATION_EXCEPTION));

    try {
      // 1. 헤더 설정
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      // 2. 멀티파트 폼 데이터 구성
      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

      // 파일 내용을 ByteArrayResource로 저장
      byte[] fileContent = file.getBytes(); // 파일의 바이트 배열
      String originalFileName = file.getOriginalFilename();
      String newFileName =
          UUID.randomUUID().toString() + "_" + originalFileName; // 랜덤한 UUID와 원래 파일 이름 결합

      // ByteArrayResource 사용
      ByteArrayResource byteArrayResource = new ByteArrayResource(fileContent) {
        @Override
        public String getFilename() {
          return newFileName; // 변경된 파일 이름
        }
      };

      body.add("file", byteArrayResource); // 변경된 파일 이름으로 파일 추가

      // 3. HttpEntity 생성 (headers와 body 결합)
      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

      // 4. POST 요청 전송
      ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST,
          requestEntity, String.class);

      // 5. 응답 처리
      if (response.getStatusCode() == HttpStatus.OK) {
        System.out.println("이미지 변환 성공: " + response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        // JSON 응답을 List<UploadResponseDto.PanelData>로 역직렬화
        List<UploadResponseDto.PanelData> uploadResponse = objectMapper.readValue(response.getBody(),
            new TypeReference<List<UploadResponseDto.PanelData>>() {});

        // 데이터 저장 및 처리
        for (UploadResponseDto.PanelData panelData : uploadResponse) {
          String description = panelData.getDescription();
          List<String> dialogueList = panelData.getDialogues(); // List<String>으로 변경
          StringBuilder conversation = new StringBuilder();

          // 각 대화 내용을 StringBuilder에 추가
          for (String dialogue : dialogueList) {
            conversation.append(dialogue).append("\n"); // 대화만 추가
          }

          int panelNumber = panelData.getPanelIndex();

          // 페이지 생성
          Long pageId = createPage(user, bookId);
          PanelRequestDto panelRequestDto = new PanelRequestDto(description,
              conversation.toString(), panelNumber);
          panelService.createPanel(user, panelRequestDto, pageId);
        }
      } else {
        System.out.println("변환 실패, 상태 코드: " + response.getStatusCode());
      }

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("이미지 변환 중 오류 발생: " + e.getMessage());
    }
  }
}
