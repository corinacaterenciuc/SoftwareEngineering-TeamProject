package theotherhalf.superconference.domain;

import com.sun.istack.NotNull;
import org.springframework.data.relational.core.mapping.Table;
import javax.persistence.*;

@Table
@Entity
public class UserNotification extends BaseEntity
{
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Notification notification;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private CMSUser user;

    private boolean read;

    public UserNotification(Notification notification, CMSUser user, boolean read) {
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

    public CMSUser getUser() {
        return user;
    }

    public void setUser(CMSUser user) {
        this.user = user;
    }
}
