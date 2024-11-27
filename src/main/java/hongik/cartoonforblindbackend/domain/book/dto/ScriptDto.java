package hongik.cartoonforblindbackend.domain.book.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ScriptDto {
  private String description;
  private String conversation;
  private int page;
  private int panel;
  private String createdAt;

}
