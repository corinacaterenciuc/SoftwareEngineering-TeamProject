package theotherhalf.superconference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import theotherhalf.superconference.domain.Proposal;
import theotherhalf.superconference.domain.Review;
import theotherhalf.superconference.dto.ReviewDTO;
import theotherhalf.superconference.exceptions.ControllerException;
import theotherhalf.superconference.services.ReviewService;

import javax.naming.ldap.Control;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/conferences")
public class ReviewController
{
    @Autowired
    private ConferenceController conferenceController;

    @Autowired
    private ReviewService reviewService;

    public ReviewController(ConferenceController conferenceController, ReviewService reviewService) {
        this.conferenceController = conferenceController;
        this.reviewService = reviewService;
    }

    @PostMapping("{confId}/proposals/{proposalId}/reviews")
    public ReviewDTO addReview(@PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestBody @Valid ReviewDTO reviewDTO)
    {
        if(null == reviewDTO.getReviewer())
        {
            throw new ControllerException("[ERROR] Null reviewer id given");
        }
        reviewDTO.setProposal(proposalId);
        //Review review = ReviewDTO.toDomain(reviewDTO);
        String reviewer = reviewDTO.getReviewer().getEmail();
        //Review rev = this.reviewService.saveReview(review, reviewer, confId, proposalId);
        Review rev = this.reviewService.addReviewToProposal(reviewDTO.getGrade(), reviewDTO.getJustification(), proposalId, confId, reviewer);
//        this.reviewService.addReview(confId, proposalId, rev);
//        return ReviewDTO.toDTO(rev, proposalId);

//        this.proposalService.fmm(theProposal, author);
//        Proposal response = this.proposalService.addConferenceProposal(confId, theProposal);
//        this.proposalService.addCoAuthorsToProposal(response, coAuthors);
//        this.proposalService.addBiddersToProposal(response, bidders);

        //this.reviewService.saveReview(rev, reviewer, confId, proposalId);
        //Review response = this.reviewService.addProposalReview(rev, proposalId);

        return ReviewDTO.toDTO(rev, proposalId);
    }

    @GetMapping("{confId}/proposals/{proposalId}/reviews")
    public List<ReviewDTO> getReviews(@PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId)
    {
        return this.reviewService.getReviews(confId, proposalId).stream().map(x -> ReviewDTO.toDTO(x, proposalId)).collect(Collectors.toList());
    }

    @DeleteMapping("{confId}/proposals/{proposalId}/reviews")
    public void deleteReview(@PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestParam("id") Long reviewId)
    {
        this.reviewService.removeReview(confId, proposalId, reviewId);
    }
}
