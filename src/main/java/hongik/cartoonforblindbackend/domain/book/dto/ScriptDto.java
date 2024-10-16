package hongik.cartoonforblindbackend.domain.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScriptDto {
  private String description;
  private String conversation;
  private Integer page;
  private Integer panel;
}
