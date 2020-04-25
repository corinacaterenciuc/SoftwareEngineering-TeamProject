package otherhalf.superconference.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notifications")
public class Notification
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Integer id;

    @Column(name = "is_read", nullable = false)
    private Boolean is_read;

    @Column(name = "notification_text", nullable = false)
    private String notificationText;

    @Column(name = "redirect_url", nullable = false)
    private String redirectUrl;

    @ManyToOne
    private CMSUser cmsUser;
}
