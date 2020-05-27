package theotherhalf.superconference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import theotherhalf.superconference.domain.Proposal;
import theotherhalf.superconference.domain.User;
import theotherhalf.superconference.dto.JsonEmailDTO;
import theotherhalf.superconference.dto.ProposalDTO;
import theotherhalf.superconference.exceptions.ControllerException;
import theotherhalf.superconference.services.ProposalService;
import theotherhalf.superconference.services.ReviewService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/conferences")
public class ProposalController
{
    @Autowired
    private ConferenceController conferenceController;

    @Autowired
    private UserController userController;

    @Autowired
    private ProposalService proposalService;

    @Autowired
    private ReviewService reviewService;

    public ProposalController(ConferenceController conferenceController, UserController userController, ProposalService proposalService)
    {
        this.conferenceController = conferenceController;
        this.userController = userController;
        this.proposalService = proposalService;
    }

    @PostMapping(path = "{confId}/proposals")
    public ProposalDTO addProposal(@PathVariable("confId") Long confId, @RequestBody ProposalDTO proposalDTO)
    {
        Proposal theProposal = ProposalDTO.toPartialDomain(proposalDTO);
        List<String> coAuthors = null;
        List<String> bidders = null;
        List<String> reviewers = null;
        String author = null;
        if(null == proposalDTO.getAuthor())
        {
            throw new ControllerException("[ERROR] Proposal must have author");
        }
        if(null != proposalDTO.getCoAuthors())
        {
            coAuthors = this.conferenceController.getEmailsFromJsonMailDTOs(proposalDTO.getCoAuthors());
        }
        if(null != proposalDTO.getBidders())
        {
            bidders = this.conferenceController.getEmailsFromJsonMailDTOs(proposalDTO.getBidders());
        }
        if(null != proposalDTO.getReviewers())
        {
            reviewers = this.conferenceController.getEmailsFromJsonMailDTOs(proposalDTO.getReviewers());
        }
        author = proposalDTO.getAuthor().getEmail();
        this.proposalService.saveAsEntity(theProposal, author);
        Proposal response = this.proposalService.addConferenceProposal(confId, theProposal);
        this.proposalService.addCoAuthorsToProposal(response, coAuthors);
        this.proposalService.addBiddersToProposal(response, bidders);
        //this.proposalService.addReviewersToProposal(response, reviewers);

        return ProposalDTO.toDTO(confId, response);
    }

    @DeleteMapping(path = "{confId}/proposals")
    public void deleteProposal(@PathVariable("confId") Long confId, @RequestParam("id") Long proposalId)
    {
        this.proposalService.removeProposal(confId, proposalId);
    }

    @GetMapping(path = "{confId}/proposals")
    public List<ProposalDTO> getAllProposals(@PathVariable("confId") Long confId)
    {
        return this.proposalService.getProposals(confId).stream().map(x -> ProposalDTO.toDTO(confId, x)).collect(Collectors.toList());
    }

    @PutMapping("{confId}/proposals/{proposalId}/reviewers")
    public void addReviewers(@PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestBody List<JsonEmailDTO> emails)
    {
        this.reviewService.addReviewers(confId, proposalId, this.conferenceController.getEmailsFromJsonMailDTOs(emails));
    }

    @DeleteMapping("{confId}/proposals/{proposalId}/reviewers")
    public void removeReviewers(@PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestBody List<JsonEmailDTO> emails)
    {
        this.reviewService.removeReviewers(confId, proposalId, this.conferenceController.getEmailsFromJsonMailDTOs(emails));
    }

    @PutMapping("{confId}/proposals/{proposalId}/bid")
    public void bidOnProposal(@PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestBody JsonEmailDTO email)
    {
        this.proposalService.addBid(confId, proposalId, email.getEmail());
    }

    @PutMapping("{confId}/proposals/{proposalId}/unbid")
    public void removeBidOnProposal(@PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestBody JsonEmailDTO email)
    {
        this.proposalService.removeBid(confId, proposalId, email.getEmail());
    }

    @PutMapping("{confId}/proposals/{proposalId}/sh")
    public void addSecondHandReviewer(@PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestBody JsonEmailDTO email)
    {
        this.proposalService.addSecondHandReviewer(confId, proposalId, email.getEmail());
    }

//    @PutMapping("{confId}/proposals/{proposalId}/sh")
//    public void addSecondHandReviewer(@PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestBody JsonEmailDTO email)
//    {
//        this.proposalService.addSecondHandReviewer(confId, proposalId, email.getEmail());
//    }
}
