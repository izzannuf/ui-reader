package id.ac.ui.cs.advprog.apifetcher.model.constraint;

public enum Host {
  CORE("CORE_HOST");

  final String value;
  Host(String variableName) {
    value = System.getenv(variableName);
  }

  public String host() {
    return value;
  }
}
