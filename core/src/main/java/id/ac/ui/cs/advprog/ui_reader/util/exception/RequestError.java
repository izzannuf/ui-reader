package id.ac.ui.cs.advprog.ui_reader.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class RequestError<T> {
  private HttpStatus httpStatus;
  private String errorMessage;
  private T content;

  public RequestError(HttpStatus httpStatus, T content) {
    this.httpStatus= httpStatus;
    this.errorMessage = "success";
    this.content = content;
  }

  public RequestError(HttpStatus httpStatus, String errorMessage, T content) {
    this.httpStatus = httpStatus;
    this.errorMessage = errorMessage;
    this.content = content;
  }

  public ResponseEntity getResponseEntity() {
    if (httpStatus.is2xxSuccessful()) {
      return ResponseEntity.ok(content);
    } else {
      return ResponseEntity.status(httpStatus).body(errorMessage);
    }
  }
}
