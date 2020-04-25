package otherhalf.superconference.entities.author;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class AuthorKey implements Serializable
{
    @Column(name = "cms_user_id")
    private Integer cmsUserId;

    @Column(name = "proposal_id")
    private Integer proposalId;
}
