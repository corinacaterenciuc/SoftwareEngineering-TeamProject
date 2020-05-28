package theotherhalf.superconference.domain;
import org.springframework.data.relational.core.mapping.Table;
import javax.persistence.*;

@Table
@Entity
public class Review extends BaseEntity
{
    //@ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="email")
    private CMSUser user;

    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "proposalId")
    private Proposal proposal;

    private ENUMERATION_GRADES grade;
    private String justification;

    public Review() {
    }

    public Review(CMSUser user, Proposal proposal, ENUMERATION_GRADES grade, String justification) {
        this.user = user;
        this.proposal = proposal;
        this.grade = grade;
        this.justification = justification;
    }

    public CMSUser getUser() {
        return user;
    }

    public void setUser(CMSUser user) {
        this.user = user;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public ENUMERATION_GRADES getGrade() {
        return grade;
    }

    public void setGrade(ENUMERATION_GRADES grade) {
        this.grade = grade;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }
}
