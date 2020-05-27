package theotherhalf.superconference.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import theotherhalf.superconference.domain.Notification;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDTO
{
    private Long id;
    private String text;
    private String href;
    private boolean read;

    public NotificationDTO(Long id, String text, String href, boolean read) {
        this.id = id;
        this.text = text;
        this.href = href;
        this.read = read;
    }

    public NotificationDTO() {
    }

    public static NotificationDTO toDTO(Notification notification, Long notifId, boolean read)
    {
        NotificationDTO notificationDTO = new NotificationDTO(notifId, notification.getText(), notification.getHref(), read);
        return notificationDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
