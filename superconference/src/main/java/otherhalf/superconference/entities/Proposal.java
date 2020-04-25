package otherhalf.superconference.entities;

import lombok.Data;
import otherhalf.superconference.entities.author.Author;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "proposals")
public class Proposal
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proposal_id")
    private Integer id;

    @Column(name="abstract", nullable = false, length = 3000)
    private String p_abstract;

    @Column(name="proposal_url")
    private String proposalUrl;

    @Column(name="presentation_url")
    private String presentationUrl;

    @ManyToOne
    private Conference conference;

    @ManyToMany
    private Set<CMSUser> bidders;

    @OneToMany(mappedBy = "author")
    private Set<Author> authors;
}
