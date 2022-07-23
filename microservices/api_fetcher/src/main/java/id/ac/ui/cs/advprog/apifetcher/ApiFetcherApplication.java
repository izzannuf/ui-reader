package id.ac.ui.cs.advprog.apifetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiFetcherApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiFetcherApplication.class, args);
  }

}
