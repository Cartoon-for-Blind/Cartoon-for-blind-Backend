package hongik.cartoonforblindbackend.domain.book.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hongik.cartoonforblindbackend.domain.book.dto.BookCreateRequestDto;
import hongik.cartoonforblindbackend.domain.page.entity.Page;
import hongik.cartoonforblindbackend.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookId;

  private String coverImageUrl;

  @Column(nullable = false)
  private String title;

  private String author;

  private String thread;

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnore
  private User user;

  @OneToMany(mappedBy = "book")
  private List<Page> pages;

  public Book(BookCreateRequestDto dto, User user){
    this.author = dto.getAuthor();
    this.coverImageUrl = dto.getCoverImageUrl();
    this.title = dto.getTitle();
    this.createdAt = new Date();
    this.updatedAt = new Date();
    this.user = user;
  }
}
