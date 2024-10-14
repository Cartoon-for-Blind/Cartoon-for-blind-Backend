package hongik.cartoonforblindbackend.domain.panel;

import hongik.cartoonforblindbackend.domain.page.Page;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "Panel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Panel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long scriptId;

  private String situation;

  private String conversation;

  private Integer panelNumber;

  @Column(name = "created_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  @ManyToOne
  @JoinColumn(name = "page_id", nullable = false)
  private Page page;
}
