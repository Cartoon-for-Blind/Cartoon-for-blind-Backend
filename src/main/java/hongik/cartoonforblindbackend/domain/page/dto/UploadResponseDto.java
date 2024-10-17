package hongik.cartoonforblindbackend.domain.page.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class UploadResponseDto {
  private List<PanelData> data; // List<PanelData> 형태로 변경

  // 내부 클래스: 패널 정보를 매핑
  public static class PanelData {
    private String description;
    private List<Dialogue> dialogues;

    @JsonProperty("panel_index") // JSON 키에 맞게 설정
    private int panelIndex;

    // 내부 클래스: dialogues 부분을 매핑
    public static class Dialogue {
      private String character; // JSON에서 다수의 대사 존재 가능

      // Getter and Setter
      public String getCharacter() {
        return character;
      }

      public void setCharacter(String character) {
        this.character = character;
      }
    }

    // Getters and Setters
    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public List<Dialogue> getDialogues() {
      return dialogues;
    }

    public void setDialogues(List<Dialogue> dialogues) {
      this.dialogues = dialogues;
    }

    public int getPanelIndex() {
      return panelIndex;
    }

    public void setPanelIndex(int panelIndex) {
      this.panelIndex = panelIndex;
    }
  }

  // Getters and Setters
  public List<PanelData> getData() {
    return data;
  }

  public void setData(List<PanelData> data) {
    this.data = data;
  }
}
