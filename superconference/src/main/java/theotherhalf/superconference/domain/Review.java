package theotherhalf.superconference.domain;

import javax.persistence.*;

@Entity
public class Review extends BaseEntity
{
    //@ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="cmsuser__id")
    private User user;

    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    private ENUMERATION_GRADES grade;
    private String justification;

    public Review() {
    }

    public Review(User user, Proposal proposal, ENUMERATION_GRADES grade, String justification) {
        this.user = user;
        this.proposal = proposal;
        this.grade = grade;
        this.justification = justification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
