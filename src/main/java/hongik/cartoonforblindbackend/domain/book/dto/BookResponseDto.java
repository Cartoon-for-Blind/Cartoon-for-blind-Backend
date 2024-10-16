package hongik.cartoonforblindbackend.domain.book.dto;

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
  private Integer lastPageNumber;
  List<ScriptDto> scriptDtoList;
}
