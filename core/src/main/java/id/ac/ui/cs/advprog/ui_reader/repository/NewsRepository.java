package id.ac.ui.cs.advprog.ui_reader.repository;

import id.ac.ui.cs.advprog.ui_reader.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {
    News findByTitle(String title);
    List<News> findByTitleContainingIgnoreCase(String keyword);
}
