package hongik.cartoonforblindbackend.domain.page.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class UploadResponseDto {

  private List<PanelData> data; // 패널 데이터 리스트

  @Data
  public static class PanelData {
    @JsonProperty("description")
    private String description;
    // 대사 부분을 List<Map<String, String>>으로 변경
    @JsonProperty("dialogue")
    private List<Map<String, String>> dialogues;

    @JsonProperty("panel_index") // JSON 키에 맞게 설정
    private int panelIndex;

    @JsonProperty("thread_id")
    private String threadId;

  }
}
