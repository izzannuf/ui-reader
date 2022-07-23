package id.ac.ui.cs.advprog.apifetcher.model.dto;

import com.google.gson.annotations.SerializedName;
import id.ac.ui.cs.advprog.apifetcher.model.dto.base.ProtectedDto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostNewsListDto extends ProtectedDto {
  @SerializedName("data")
  List<PostNewsDto> postNewsDtoList;
}
