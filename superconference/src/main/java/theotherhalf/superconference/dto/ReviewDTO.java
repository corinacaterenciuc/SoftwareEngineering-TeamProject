package theotherhalf.superconference.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import theotherhalf.superconference.domain.ENUMERATION_GRADES;
import theotherhalf.superconference.domain.Proposal;
import theotherhalf.superconference.domain.Review;
import theotherhalf.superconference.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewDTO
{
    private Long id;
    private JsonEmailDTO reviewer;
    private Long proposal;
    private String justification;
    private ENUMERATION_GRADES grade;

    public ReviewDTO(Long id, JsonEmailDTO reviewer, Long proposal, String justification, ENUMERATION_GRADES grade) {
        this.id = id;
        this.reviewer = reviewer;
        this.proposal = proposal;
        this.justification = justification;
        this.grade = grade;
    }

    public ReviewDTO() {
    }

    public static Review toDomain(ReviewDTO reviewDTO)
    {
        Review review = new Review();
        if(null != reviewDTO.getId())
        {
            review.setID(reviewDTO.getId());
        }
        if(null != reviewDTO.getGrade())
        {
            review.setGrade(reviewDTO.getGrade());
        }
        if(null != reviewDTO.getJustification())
        {
            review.setJustification(reviewDTO.getJustification());
        }
        return review;
    }

    public static ReviewDTO toDTO(Review review, Long proposalId)
    {
        ReviewDTO reviewDTO = new ReviewDTO();
        if(null != review.getID())
        {
            reviewDTO.setId(review.getID());
        }
        if(null != review.getJustification())
        {
            reviewDTO.setJustification(review.getJustification());
        }
        if(null != review.getUser())
        {
            reviewDTO.setReviewer(new JsonEmailDTO(review.getUser().getEmail()));
        }
        if(null != review.getGrade())
        {
            reviewDTO.setGrade(review.getGrade());
        }
        reviewDTO.setProposal(proposalId);
        return reviewDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JsonEmailDTO getReviewer() {
        return reviewer;
    }

    public void setReviewer(JsonEmailDTO reviewer) {
        this.reviewer = reviewer;
    }

    public Long getProposal() {
        return proposal;
    }

    public void setProposal(Long proposal) {
        this.proposal = proposal;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public ENUMERATION_GRADES getGrade() {
        return grade;
    }

    public void setGrade(ENUMERATION_GRADES grade) {
        this.grade = grade;
    }
}
