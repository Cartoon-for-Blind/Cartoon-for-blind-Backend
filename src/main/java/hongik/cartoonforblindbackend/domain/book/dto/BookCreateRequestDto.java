package hongik.cartoonforblindbackend.domain.book.dto;

import hongik.cartoonforblindbackend.domain.book.entity.Book;
import hongik.cartoonforblindbackend.domain.user.dto.UserDto;
import hongik.cartoonforblindbackend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class BookCreateRequestDto{
  private String coverImageUrl;
  private String title;
  private String author;

  public void setCoverImageUrl(String imageUrl) {
    this.coverImageUrl = imageUrl;
  }
}
