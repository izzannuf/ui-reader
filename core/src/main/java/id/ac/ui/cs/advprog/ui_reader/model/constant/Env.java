package id.ac.ui.cs.advprog.ui_reader.model.constant;

public enum Env {
  EMAILBLASTERHOST("EMAIL_BLASTER_HOST"),
  APIKEY("API_KEY");

  final String val;
  Env(String variableName) {
    val = System.getenv(variableName);
  }

  public String val() {
    return val;
  }
}
