package id.ac.ui.cs.advprog.apifetcher.model.dto.base;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class ProtectedDto {
  @SerializedName("api-key")
  String apiKey;
}
