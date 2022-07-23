package id.ac.ui.cs.advprog.ui_reader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.Generated;

import javax.persistence.*;

@Generated
@Entity @Data
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    // getters and setters are not shown
    @ManyToMany
    @JoinTable(
        name = "user_news_provider_subscription",
        joinColumns = @JoinColumn(name = "users_id"),
        inverseJoinColumns = @JoinColumn(name = "news_provider_id"))
    @JsonIgnoreProperties({"subscribedUser", "news"})
    List<NewsProvider> subscription;
}
