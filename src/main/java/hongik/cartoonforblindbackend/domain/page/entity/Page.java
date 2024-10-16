package hongik.cartoonforblindbackend.domain.page.entity;

import hongik.cartoonforblindbackend.domain.book.entity.Book;
import hongik.cartoonforblindbackend.domain.panel.Panel;
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
@Table(name = "Page")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Page {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pageId;

  private Integer pageNumber;

  @Column(name = "created_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  @OneToMany(mappedBy = "page")
  private List<Panel> panels;

  @ManyToOne
  @JoinColumn(name = "book_id", nullable = false)
  private Book book;
}
