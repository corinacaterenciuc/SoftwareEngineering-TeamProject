package theotherhalf.superconference.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class UserNotification extends BaseEntity
{
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Notification notification;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private User user;

    private boolean read;

    public UserNotification(Notification notification, User user, boolean read) {
        this.notification = notification;
        this.user = user;
        this.read = read;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public UserNotification() {
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification conference) {
        this.notification = conference;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
