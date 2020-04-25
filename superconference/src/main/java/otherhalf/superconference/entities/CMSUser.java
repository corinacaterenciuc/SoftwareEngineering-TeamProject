package otherhalf.superconference.entities;

import lombok.Data;
import otherhalf.superconference.entities.author.Author;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class CMSUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cms_user_id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToMany
    private Set<Proposal> bids;

    @OneToMany(mappedBy = "author")
    private Set<Author> authored;

    @OneToMany
    private Set<Notification> notifications;
}
