package hongik.cartoonforblindbackend.domain.panel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hongik.cartoonforblindbackend.domain.dialogue.entity.Dialogue;
import hongik.cartoonforblindbackend.domain.page.entity.Page;
import hongik.cartoonforblindbackend.domain.panel.dto.PanelRequestDto;
import jakarta.persistence.CascadeType;
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
import java.util.List;
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
  private Integer panelNumber;

  @Column
  private String description;



  @Column(name = "created_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  @ManyToOne
  @JoinColumn(name = "page_id", nullable = false)
  @JsonIgnore
  private Page page;

  @OneToMany(mappedBy = "panel",cascade = CascadeType.REMOVE)
  private List<Dialogue> dialogues;

  public Panel(PanelRequestDto panelRequestDto, Page page) {
    this.panelNumber = panelRequestDto.getPanelNumber();
    this.description = panelRequestDto.getDescription();
    this.page =page;
    this.createdAt = new Date();
    this.updatedAt = new Date();
  }
}
