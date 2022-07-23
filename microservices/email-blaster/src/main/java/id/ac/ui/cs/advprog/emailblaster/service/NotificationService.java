package id.ac.ui.cs.advprog.emailblaster.service;


import id.ac.ui.cs.advprog.emailblaster.model.dto.NotificationListDto;

public interface NotificationService {
  void sentNotification(NotificationListDto notificationListDto);
}
