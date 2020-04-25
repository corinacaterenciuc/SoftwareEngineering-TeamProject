package otherhalf.superconference.entities.author;

import lombok.Data;
import otherhalf.superconference.entities.CMSUser;
import otherhalf.superconference.entities.Proposal;

import javax.persistence.*;

@Data
@Entity
public class Author
{
    @EmbeddedId
    private AuthorKey id;

    @ManyToOne
    @MapsId("cms_user_id")
    @JoinColumn(name = "cms_user_id")
    private CMSUser author;

    @ManyToOne
    @MapsId("proposal_id")
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    @Column(name = "is_main_author", nullable = false)
    private boolean is_main_author;
}
