package id.ac.ui.cs.advprog.ui_reader.util.exception;

import org.springframework.http.ResponseEntity;

public class RequestErrorException extends Exception {
  final transient RequestError requestError;

  public RequestErrorException(RequestError requestError) {
    super(requestError.getErrorMessage());
    this.requestError = requestError;
  }

  public ResponseEntity getResponseEntity() {
    return requestError.getResponseEntity();
  }
}
