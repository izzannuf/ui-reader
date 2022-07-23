package id.ac.ui.cs.advprog.apifetcher.model.constraint;

public enum ApiKey {
  NEWSAPIORG("NEWS_API_ORG_API_KEY"),
  NEWSDATA("NEWS_DATA_API_KEY"),
  CORE("API_KEY");

  final String value;
  ApiKey(String variableName) {
    value = System.getenv(variableName);
  }

  public String apiKey() {
    return value;
  }
}
