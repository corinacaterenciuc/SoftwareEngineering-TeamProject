package theotherhalf.superconference.domain;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Entity
public class Proposal extends BaseEntity
{

    @NotNull
    @Column(name = "name")
    private String proposalName;

    private String filePath;
    private String abstractDescription;

    @ElementCollection
    private List<String> topics;

    @ElementCollection
    private List<String> keywords;

    //@ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="id", nullable = false)
    private CMSUser author;

    //@ManyToMany(cascade = CascadeType.ALL)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "proposal_coauthors")
    @JoinColumn(name="cmsuser__id", nullable=true)
    private List<CMSUser> coAuthors;

    //@ManyToMany(cascade = CascadeType.ALL)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "proposal_bidders")
    @JoinColumn(name="id", nullable=true)
    private List<CMSUser> biddingPeople;

    //@ManyToMany(cascade = CascadeType.ALL)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "proposal_reviewers")
    @JoinColumn(name="id", nullable=true)
    private List<CMSUser> reviewers;

    @OneToMany(mappedBy = "proposal", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "proposal_shreviewer")
    @JoinColumn(name="id", nullable = true)
    private CMSUser secondHandReviewer;

    public Proposal(@NotNull String proposalName, String filePath, String abstractDescription, List<String> topics, List<String> keywords) {
        this.proposalName = proposalName;
        this.filePath = filePath;
        this.abstractDescription = abstractDescription;
        this.topics = topics;
        this.keywords = keywords;
        this.biddingPeople = new ArrayList<>();
        this.coAuthors = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.reviewers = new ArrayList<>();
    }

    public Proposal(@NotNull String proposalName, String filePath, String abstractDescription, List<String> topics, List<String> keywords, CMSUser author, List<CMSUser> coAuthors, List<CMSUser> biddingPeople, List<CMSUser> reviewers, List<Review> reviews) {
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

    public CMSUser getAuthor() {
        return author;
    }

    public void setAuthor(CMSUser author) {
        this.author = author;
    }

    public List<CMSUser> getCoAuthors() {
        return coAuthors;
    }

    public void setCoAuthors(List<CMSUser> coAuthors) {
        this.coAuthors = coAuthors;
    }

    public CMSUser getSecondHandReviewer() {
        return secondHandReviewer;
    }

    public void setSecondHandReviewer(CMSUser secondHandReviewer) {
        this.secondHandReviewer = secondHandReviewer;
    }

    public List<CMSUser> getBiddingPeople() {
        return biddingPeople;
    }

    public void setBiddingPeople(List<CMSUser> biddingPeople) {
        this.biddingPeople = biddingPeople;
    }

    public List<CMSUser> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<CMSUser> reviewers) {
        this.reviewers = reviewers;
    }

    public void removeReviewers(List<CMSUser> reviewers)
    {
        this.reviewers = this.reviewers.stream().filter(x -> reviewers.stream().noneMatch(y -> y.equals(x))).collect(Collectors.toList());
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public boolean hasReview(Long reviewId)
    {
        return this.reviews.stream().anyMatch(x -> x.getID().equals(reviewId));
    }

    public boolean hasReviewFromUser(String userEmail)
    {
        return this.reviews.stream().anyMatch(x -> x.getUser().getEmail().equals(userEmail));
    }

    public boolean isReviewer(String userEmail)
    {
        return this.reviewers.stream().anyMatch(x -> x.getEmail().equals(userEmail));
    }
    @Transactional
    public Review addReview(Review review)
    {
        if(this.reviews.add(review))
        {
            return review;
        }
        throw new ValidationException("[ERROR] Review couldn't be added");
    }

    @Transactional
    public void removeReview(Long reviewId)
    {
        this.reviews = this.reviews.stream().filter(x -> !x.getID().equals(reviewId)).collect(Collectors.toList());
    }

    @Transactional
    public void updateReview(Review review)
    {
        if(null == review.getID())
        {
            throw new ValidationException("[ERROR] Invalid review id given");
        }
        Review oldReview = this.reviews.stream().filter(x -> x.getID().equals(review.getID())).findFirst().get();
        if(!review.getUser().equals(oldReview.getUser()))
        {
            throw new ValidationException("[ERROR] Reviewer email can't be changed.");
        }

        if(null != review.getGrade())
        {
            oldReview.setGrade(review.getGrade());
        }
        if(null != review.getJustification())
        {
            oldReview.setJustification(review.getJustification());
        }
    }

    @Transactional
    public void addBidder(CMSUser usr)
    {
        if(this.biddingPeople.stream().noneMatch(x -> x.getEmail().equals(usr.getEmail())))
        {
            this.biddingPeople.add(usr);
        }
        else
        {
            throw new ValidationException("[ERROR] Already bidden");
        }
    }

    @Transactional
    public void removeBidder(CMSUser usr)
    {
        this.biddingPeople.remove(usr);
    }

    @Transactional
    public void addSecondHandReviewer(CMSUser usr)
    {
        if(null != this.secondHandReviewer)
        {
            throw new ValidationException("[ERROR] Second hand reviewer already assigned.");
        }
        this.secondHandReviewer = usr;
    }

    @Transactional
    public void removeSecondHandReviewer(CMSUser usr)
    {
        this.secondHandReviewer = null;
    }

}
