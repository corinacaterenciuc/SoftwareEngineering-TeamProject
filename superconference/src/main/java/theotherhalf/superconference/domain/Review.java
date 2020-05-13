package theotherhalf.superconference.domain;

import javax.persistence.*;

@Entity
public class Review extends BaseEntity
{
    @ManyToOne
    @JoinColumn(name="cmsuser__id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    private ENUMERATION_GRADES grade;
    private String justification;
}
