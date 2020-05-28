package theotherhalf.superconference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import theotherhalf.superconference.domain.Notification;
import theotherhalf.superconference.domain.CMSUser;
import theotherhalf.superconference.domain.UserNotification;
import theotherhalf.superconference.dto.JsonEmailDTO;
import theotherhalf.superconference.dto.NotificationDTO;
import theotherhalf.superconference.services.NotificationService;
import theotherhalf.superconference.services.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/notifications")
public class NotificationController
{
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public NotificationController() {
    }
    
    @GetMapping
    public List<NotificationDTO> getAllNotifications()
    {
        List<UserNotification> notifications = this.notificationService.getAllNotifications();
        return notifications.stream().map(x -> NotificationDTO.toDTO(x.getNotification(), x.getID(), x.isRead())).collect(Collectors.toList());
    }

    @GetMapping("{email}")
    public List<NotificationDTO> getNotificationsForUser(@PathVariable("email") JsonEmailDTO email)
    {
        List<UserNotification> notifications = this.notificationService.getAllNotificationsForUser(email.getEmail());
        return notifications.stream().map(x -> NotificationDTO.toDTO(x.getNotification(), x.getID(), x.isRead())).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("{notifId}")
    public void markNotificationAsRead(@PathVariable("notifId") Long notifId)
    {
        this.notificationService.markAsRead(notifId);
    }

}
