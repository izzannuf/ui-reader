package id.ac.ui.cs.advprog.emailblaster.controller;


import id.ac.ui.cs.advprog.emailblaster.model.dto.NotificationListDto;
import id.ac.ui.cs.advprog.emailblaster.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/notification")
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  @PostMapping(produces = {"application/json"})
  @ResponseBody
  public ResponseEntity<String> postNewsList(@RequestBody NotificationListDto notificationListDto) {
    notificationService.sentNotification(notificationListDto);
    return ResponseEntity.ok("ok");
  }
}
