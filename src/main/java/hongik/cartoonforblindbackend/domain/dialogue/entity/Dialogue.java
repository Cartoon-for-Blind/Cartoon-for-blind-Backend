package hongik.cartoonforblindbackend.domain.dialogue.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hongik.cartoonforblindbackend.domain.page.entity.Page;
import hongik.cartoonforblindbackend.domain.panel.entity.Panel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Dialogue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dialogue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long dialogueId;

  private String speaker;
  private String dialogue;

  @ManyToOne
  @JoinColumn(name = "panel_id", nullable = false)
  @JsonIgnore
  private Panel panel;


  public Dialogue(String key, String value, Panel panel) {
    this.speaker = key;
    this.dialogue = value;
    this.panel = panel;
  }
}
