package hongik.cartoonforblindbackend.domain.panel.entity;

import hongik.cartoonforblindbackend.domain.page.entity.Page;
import hongik.cartoonforblindbackend.domain.panel.dto.PanelRequestDto;
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
  private Long panelId;

  @Column
  private String description;

  @Column
  private String conversation;

  @Column
  private Integer panelNumber;

  @Column
  private String panelImageUrl;

  @Column(name = "created_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  @ManyToOne
  @JoinColumn(name = "page_id", nullable = false)
  private Page page;

  public Panel(PanelRequestDto panelRequestDto, Page page) {
    this.panelImageUrl = panelRequestDto.getPanelImageUrl();
    this.conversation = panelRequestDto.getConversation();
    this.description = panelRequestDto.getConversation();
    this.panelNumber = panelRequestDto.getPanelNumber();
    this.page =page;
  }
}
