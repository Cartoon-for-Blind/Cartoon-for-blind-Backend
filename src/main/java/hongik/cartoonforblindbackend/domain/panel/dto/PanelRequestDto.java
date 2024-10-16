package hongik.cartoonforblindbackend.domain.panel.dto;

import lombok.Data;

@Data
public class PanelRequestDto {
  private String description;
  private String conversation;
  private Integer panelNumber;
  private String panelImageUrl;

}
