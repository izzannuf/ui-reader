package id.ac.ui.cs.advprog.apifetcher.dummy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public enum Dummy {
  NEWSAPIORGRESPONSE("NewsApiOrgDummyResponse"),
  NEWSDATARESPONSE("NewsDataDummyResponse");

  private final String content;
  private final String dummiesPath = ".\\src\\test\\java\\id\\ac\\ui\\cs\\advprog\\apifetcher\\dummy\\files";

  Dummy(String filePath) {
    try {
      File file = new File(dummiesPath + "\\" + filePath);
      Scanner sc = new Scanner(file);
      sc.useDelimiter("\\Z");
      content = sc.next();
    } catch (FileNotFoundException e) {
      throw new ExceptionInInitializerError(e.getMessage());
    }
  }

  public String content() {
    return content;
  }

}
