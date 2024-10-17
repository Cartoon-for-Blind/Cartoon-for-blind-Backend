package hongik.cartoonforblindbackend.domain.panel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PanelRequestDto {
  private String description;
  private String conversation;
  private Integer panelNumber;

}
