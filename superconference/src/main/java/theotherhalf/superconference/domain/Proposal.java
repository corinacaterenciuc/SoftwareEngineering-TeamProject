package theotherhalf.superconference.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Proposal extends BaseEntity
{

    @NotNull
    @Column(name = "proposal_name")
    private String proposalName;

    private String filePath;
    private String abstractDescription;

    @ElementCollection
    private List<String> topics;

    @ElementCollection
    private List<String> keywords;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cmsuser__id", nullable = false)
    private User author;

    @ManyToMany
    @JoinColumn(name="cmsuser__id")
    private List<User> coAuthors;

    @ManyToMany
    @JoinColumn(name="cmsuser__id")
    private List<User> biddingPeople;

    @ManyToMany
    @JoinColumn(name="cmsuser__id")
    private List<User> reviewers;

    @OneToMany(mappedBy = "proposal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews;

}
