package otherhalf.superconference.entities.evaluation;

import lombok.Data;
import otherhalf.superconference.entities.CMSUser;
import otherhalf.superconference.entities.Proposal;

import javax.persistence.*;

@Data
@Entity
public class Evaluation
{
    @EmbeddedId
    private EvaluationKey id;

    @ManyToOne
    @MapsId("cms_user_id")
    @JoinColumn(name = "cms_user_id")
    private CMSUser reviewer;

    @ManyToOne
    @MapsId("proposal_id")
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "evaluation_grade", nullable = false)
    private EvaluationGrade grade;

    @Column(name = "motivation", length = 3000, nullable = false)
    private String motivation;

    /*
    Specification requires allowing Authors to see Reviewer
    justifications if the Proposal is accepted. This boolean
    allows MainAuthor to check off suggestions that were
    accounted for.
    */
    @Column(name = "author_checked", nullable = false)
    private Boolean isCheckedAuthor;
}
