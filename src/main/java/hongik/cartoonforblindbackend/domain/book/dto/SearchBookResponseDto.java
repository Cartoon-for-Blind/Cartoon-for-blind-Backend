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

  public SearchBookResponseDto(Book book) {
    this.bookId = book.getBookId();
    this.bookCoverImageUrl = book.getCoverImageUrl();
    this.title = book.getTitle();
    this.author = book.getAuthor();
  }
}
