package id.ac.ui.cs.advprog.ui_reader.model.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import id.ac.ui.cs.advprog.ui_reader.model.constant.Env;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestError;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestErrorException;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public abstract class ProtectedDto {
  @NotNull
  @JsonProperty("api-key")
  String apiKey;

  public void validate() throws RequestErrorException {
    if (!apiKey.equals(Env.APIKEY.val())) {
      RequestError<String> requestError = new RequestError<>(
          HttpStatus.UNAUTHORIZED,
          "Your api key is invalid",
          null);
      throw new RequestErrorException(requestError);
    }
  }
}
