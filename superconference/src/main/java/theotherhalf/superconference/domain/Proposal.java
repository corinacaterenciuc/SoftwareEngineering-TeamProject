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

    @OneToMany(mappedBy = "proposal", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public Proposal(@NotNull String proposalName, String filePath, String abstractDescription, List<String> topics, List<String> keywords, User author, List<User> coAuthors, List<User> biddingPeople, List<User> reviewers, List<Review> reviews) {
        this.proposalName = proposalName;
        this.filePath = filePath;
        this.abstractDescription = abstractDescription;
        this.topics = topics;
        this.keywords = keywords;
        this.author = author;
        this.coAuthors = coAuthors;
        this.biddingPeople = biddingPeople;
        this.reviewers = reviewers;
        this.reviews = reviews;
    }

    public Proposal() {
    }

    public String getProposalName() {
        return proposalName;
    }

    public void setProposalName(String proposalName) {
        this.proposalName = proposalName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAbstractDescription() {
        return abstractDescription;
    }

    public void setAbstractDescription(String abstractDescription) {
        this.abstractDescription = abstractDescription;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<User> getCoAuthors() {
        return coAuthors;
    }

    public void setCoAuthors(List<User> coAuthors) {
        this.coAuthors = coAuthors;
    }

    public List<User> getBiddingPeople() {
        return biddingPeople;
    }

    public void setBiddingPeople(List<User> biddingPeople) {
        this.biddingPeople = biddingPeople;
    }

    public List<User> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<User> reviewers) {
        this.reviewers = reviewers;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
