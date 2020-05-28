package theotherhalf.superconference.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theotherhalf.superconference.domain.Notification;
import theotherhalf.superconference.domain.CMSUser;
import theotherhalf.superconference.domain.UserNotification;
import theotherhalf.superconference.repository.NotificationRepository;
import theotherhalf.superconference.repository.UserNotificationRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NotificationService
{
    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    public NotificationService()
    {

    }

    public NotificationService(UserService userService) {
        this.userService = userService;
    }

    public List<UserNotification> getAllNotifications()
    {
        return this.userNotificationRepository.findAll();
    }

    @Transactional
    public void addNotification(UserNotification userNotification)
    {
        this.userNotificationRepository.save(userNotification);
    }

    public List<UserNotification> getAllNotificationsForUser(String email)
    {
        CMSUser usr = this.userService.getUserAfterValidation(email);
        return this.userNotificationRepository.findByUser(usr);
    }

    @Transactional
    public void markAsRead(Long notifId)
    {
        if(this.userNotificationRepository.findById(notifId).isPresent())
        {
            UserNotification userNotification = this.userNotificationRepository.findById(notifId).get();
            userNotification.setRead(true);
        }
    }
}
