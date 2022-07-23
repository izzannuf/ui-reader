package id.ac.ui.cs.advprog.ui_reader.repository;

import id.ac.ui.cs.advprog.ui_reader.model.News;
import id.ac.ui.cs.advprog.ui_reader.model.NewsProvider;
import id.ac.ui.cs.advprog.ui_reader.model.User;
import id.ac.ui.cs.advprog.ui_reader.model.dto.NotificationDto;
import id.ac.ui.cs.advprog.ui_reader.model.dto.NotificationListDto;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsDto;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.ui_reader.util.email.EmailBlasterUtility;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestError;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestErrorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NewsProviderUtility {
  @Autowired
  NewsProviderRepository newsProviderRepository;

  @Autowired
  NewsRepository newsRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  EmailBlasterUtility emailBlasterUtility;

  public NewsProvider getOrCreateProvider(String providerId) {
    if (!newsProviderRepository.existsById(providerId)) {
      var newsProvider = new NewsProvider(providerId, Collections.emptySet(), Collections.emptySet());
      newsProviderRepository.save(newsProvider);
      return newsProvider;
    } else {
      return newsProviderRepository.findById(providerId).get();
    }
  }

  public List<News> convertPostNewsDtoToNews(PostNewsListDto postNewsListDto) {
    List<News> result = new ArrayList<>();
    for (PostNewsDto postNewsDto : postNewsListDto.getPostNewsDtoList()) {
      var newsProvider = getOrCreateProvider(postNewsDto.getProviderId());
      var news = postNewsDto.toNewsWithProvider(newsProvider);
      result.add(news);
    }
    return result;
  }

  public void checkForNewNewsAndNotifySubcribers(List<News> newsList) {
    Map<String, List<String>> targetedUser = new HashMap<>();

    for (News news : newsList) {
      if (newsRepository.existsById(news.getId())) {

        var newsProvider = news.getProvider();
        for (User user : newsProvider.getSubscribedUser()) {
          String targetedEmail = user.getEmail();
          List<String> urlList;

          if (targetedUser.containsKey(targetedEmail)) {
            urlList = targetedUser.get(targetedEmail);
          } else {
            urlList = new ArrayList<>();
          }
          urlList.add(news.getSource());
          targetedUser.put(targetedEmail, urlList);
        }
      }
    }

    if (!targetedUser.isEmpty()) {
      List<NotificationDto> notificationDtoList = new ArrayList<>();
      for (Map.Entry<String, List<String>> entry : targetedUser.entrySet()) {
        notificationDtoList.add(new NotificationDto(entry.getKey(), entry.getValue()));
      }
      var notificationListDto = new NotificationListDto();
      notificationListDto.setNotificationDtos(notificationDtoList);

      emailBlasterUtility.sendNotification(notificationListDto);
    }
  }

  public NewsProvider getNewsProvider(String providerId) throws RequestErrorException {
    Optional<NewsProvider> optionalNewsProvider = newsProviderRepository.findById(providerId);
    if (optionalNewsProvider.isPresent()) {
      return optionalNewsProvider.get();
    } else {
      RequestError<String> requestError = new RequestError<>(
          HttpStatus.NOT_FOUND,
          "News provider not found.",
          null);
      throw new RequestErrorException(requestError);
    }
  }

  public boolean isUserSubcribed(User user, String providerId) throws RequestErrorException {
    var newsProvider = getNewsProvider(providerId);
    Set<User> userSet = newsProvider.getSubscribedUser();
    return userSet.contains(user);
  }

  public void subscribeUser(User user, String providerId) throws RequestErrorException {
    var newsProvider = getNewsProvider(providerId);

    List<NewsProvider> subscription = user.getSubscription();
    if (subscription.contains(newsProvider)) {
      subscription.remove(newsProvider);
    } else {
      subscription.add(newsProvider);
    }
    user.setSubscription(subscription);
    userRepository.save(user);
  }
}
