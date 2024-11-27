package hongik.cartoonforblindbackend.domain.book.dto;

import hongik.cartoonforblindbackend.domain.page.entity.Page;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookResponseDto {

  private Long bookId;
  private String bookCoverImageUrl;
  private String title;
  private String author;
  List<ScriptDto> scriptDtoList;
}
