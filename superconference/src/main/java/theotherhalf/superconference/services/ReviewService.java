package theotherhalf.superconference.services;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theotherhalf.superconference.domain.*;
import theotherhalf.superconference.exceptions.ServiceException;
import theotherhalf.superconference.repository.ReviewRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;


@Service
public class ReviewService
{

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProposalService proposalService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewService()
    {

    }


    public List<Review> getReviews(Long confId, Long proposalId)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Section main = conference.getDefaultSection();
        Proposal proposal = main.getProposal(proposalId);
        return proposal.getReviews();
    }

    @Transactional
    public Review addReviewToProposal(ENUMERATION_GRADES grade, String just, Long proposalId, Long conferenceId, String userEmail)
    {
        CMSUser user = this.userService.getUserAfterValidation(userEmail);
        Conference conference = this.conferenceService.getConferenceAfterValidation(conferenceId);
        Section main = conference.getDefaultSection();
        Proposal proposal = main.getProposal(proposalId);
        Review review = new Review(user, proposal, grade, just);
        //review.setID(new Random().nextLong());
        if(proposal.hasReviewFromUser(userEmail))
        {
            throw new ServiceException("[ERROR] Reviewer already sent a review");
        }
        if(!proposal.isReviewer(userEmail))
        {
            throw new ServiceException("[ERROR] User is not an assigned reviewer");
        }
        proposal.addReview(review);
        this.reviewRepository.save(review);
        review.setID(review.getID());
        return review;
    }

    @Transactional
    public void removeReview(Long confId, Long proposalId, Long reviewId)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Section main = conference.getDefaultSection();
        Proposal proposal = main.getProposal(proposalId);
        proposal.removeReview(reviewId);
        this.reviewRepository.deleteById(reviewId);
    }

    @Transactional
    public void updateReview(Long confId, Long proposalId, Review review, String userEmail)
    {
        CMSUser user = this.userService.getUserAfterValidation(userEmail);
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        review.setUser(user);
        Section main = conference.getDefaultSection();
        Proposal proposal = main.getProposal(proposalId);
        proposal.updateReview(review);
    }

    @Transactional
    public void addReviewers(Long confId, Long proposalId, List<String> userEmails)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Proposal proposal = conference.getDefaultSection().getProposal(proposalId);
        this.proposalService.addReviewersToProposal(proposal, confId, userEmails);
    }

    @Transactional
    public void removeReviewers(Long confId, Long proposalId, List<String> userEmails)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Proposal proposal = conference.getDefaultSection().getProposal(proposalId);
        this.proposalService.removeReviewersFromProposal(proposal, userEmails);
    }
}
