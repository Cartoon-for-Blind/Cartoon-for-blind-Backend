package hongik.cartoonforblindbackend.domain.page.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class UploadResponseDto {

  private List<PanelData> data; // 패널 데이터 리스트

  @Data
  public static class PanelData {
    private String description;
    private List<String> dialogues; // 대사 부분을 List<String>으로 변경

    @JsonProperty("panel_index") // JSON 키에 맞게 설정
    private int panelIndex;
  }
}
