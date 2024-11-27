package hongik.cartoonforblindbackend.domain.book.dto;

import hongik.cartoonforblindbackend.domain.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SearchBookResponseDto {
  private Long bookId;
  private String bookCoverImageUrl;
  private String title;
  private String author;
  private String updatedAt;

  public SearchBookResponseDto(Book book,String updatedAt) {
    this.bookId = book.getBookId();
    this.title = book.getTitle();
    this.author = book.getAuthor();
    this.updatedAt = updatedAt;
  }
}
