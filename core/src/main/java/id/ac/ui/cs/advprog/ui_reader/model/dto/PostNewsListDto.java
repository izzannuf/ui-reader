package id.ac.ui.cs.advprog.ui_reader.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import id.ac.ui.cs.advprog.ui_reader.model.dto.base.ProtectedDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostNewsListDto extends ProtectedDto {
  @NotNull
  @JsonProperty("data")
  List<PostNewsDto> postNewsDtoList;
}
