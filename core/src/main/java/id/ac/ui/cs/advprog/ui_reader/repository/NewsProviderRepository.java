package id.ac.ui.cs.advprog.ui_reader.repository;

import id.ac.ui.cs.advprog.ui_reader.model.NewsProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsProviderRepository extends JpaRepository<NewsProvider, String> {

}
