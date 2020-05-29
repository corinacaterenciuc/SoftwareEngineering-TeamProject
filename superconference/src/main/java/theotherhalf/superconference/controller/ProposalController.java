package theotherhalf.superconference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import theotherhalf.superconference.domain.Proposal;
import theotherhalf.superconference.domain.CMSUser;
import theotherhalf.superconference.dto.JsonEmailDTO;
import theotherhalf.superconference.dto.ProposalDTO;
import theotherhalf.superconference.exceptions.CMSForbidden;
import theotherhalf.superconference.exceptions.ControllerException;
import theotherhalf.superconference.services.ProposalService;
import theotherhalf.superconference.services.ReviewService;
import theotherhalf.superconference.services.UserService;

import javax.transaction.Transactional;
import java.awt.desktop.UserSessionEvent;
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

    @Autowired
    private UserService userService;

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
        this.proposalService.addBiddersToProposal(response, confId, bidders);
        //this.proposalService.addReviewersToProposal(response, reviewers);

        return ProposalDTO.toDTO(confId, response);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "{confId}/proposals")
    public void updateProposal(@RequestHeader(name="Authorization") String token, @PathVariable("confId") Long confId, @RequestBody ProposalDTO proposalDTO)
    {
        if(null == proposalDTO.getId())
        {
            throw new ControllerException("[ERROR] Null proposal id given to update");
        }

        String tokenEmail = this.userController.getEmailFromToken(token);
        if(!this.proposalService.getProposal(confId, proposalDTO.getId()).getAuthor().getEmail().equals(tokenEmail)
          && this.proposalService.getProposal(confId, proposalDTO.getId()).getCoAuthors().stream().noneMatch(x -> x.getEmail().equals(token)))
        {
            throw new CMSForbidden("[ERROR] No rights to update proposal");
        }

        Proposal theProposal = ProposalDTO.toPartialDomain(proposalDTO);
        theProposal.setID(proposalDTO.getId());
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

        Proposal response = this.proposalService.updateProposal(confId, theProposal);
        this.proposalService.addCoAuthorsToProposal(response, coAuthors);
        this.proposalService.addBiddersToProposal(response, confId, bidders);
        this.proposalService.addReviewersToProposal(response, confId, reviewers);

        //return ProposalDTO.toDTO(confId, response);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{confId}/proposals")
    public void deleteProposal(@RequestHeader(name="Authorization") String token, @PathVariable("confId") Long confId, @RequestParam("id") Long proposalId)
    {
        String tokenEmail = this.userController.getEmailFromToken(token);
        if(!this.proposalService.getProposal(confId, proposalId).getAuthor().getEmail().equals(tokenEmail))
        {
            throw new CMSForbidden("[ERROR] No rights to delete proposal");
        }

        this.proposalService.removeProposal(confId, proposalId);
    }

    @GetMapping(path = "{confId}/proposals")
    public List<ProposalDTO> getAllProposals(@PathVariable("confId") Long confId)
    {
        return this.proposalService.getProposals(confId).stream().map(x -> ProposalDTO.toDTO(confId, x)).collect(Collectors.toList());
    }

    @PutMapping("{confId}/proposals/{proposalId}/reviewers")
    public void addReviewers(@RequestHeader(name="Authorization") String token, @PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestBody List<JsonEmailDTO> emails)
    {
        String tokenEmail = this.userController.getEmailFromToken(token);
        if(!this.userService.isAnySCM(tokenEmail, confId))
        {
            throw new CMSForbidden("[ERROR] No rights to add reviewers");
        }
        this.reviewService.addReviewers(confId, proposalId, this.conferenceController.getEmailsFromJsonMailDTOs(emails));
    }

    @DeleteMapping("{confId}/proposals/{proposalId}/reviewers")
    public void removeReviewers(@RequestHeader(name="Authorization") String token, @PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestBody List<JsonEmailDTO> emails)
    {
        String tokenEmail = this.userController.getEmailFromToken(token);
        if(!this.userService.isAnySCM(tokenEmail, confId))
        {
            throw new CMSForbidden("[ERROR] No rights to delete reviewers");
        }
        this.reviewService.removeReviewers(confId, proposalId, this.conferenceController.getEmailsFromJsonMailDTOs(emails));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{confId}/proposals/{proposalId}/bid")
    public void bidOnProposal(@RequestHeader(name="Authorization") String token, @PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestBody JsonEmailDTO email)
    {
        String tokenEmail = this.userController.getEmailFromToken(token);
        if(!this.userService.isAnyPCM(tokenEmail, confId))
        {
            throw new CMSForbidden("[ERROR] No rights to bid on proposal");
        }
        this.proposalService.addBid(confId, proposalId, email.getEmail());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{confId}/proposals/{proposalId}/unbid")
    public void removeBidOnProposal(@RequestHeader(name="Authorization") String token, @PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestBody JsonEmailDTO email)
    {
        String tokenEmail = this.userController.getEmailFromToken(token);
        if(!this.userService.isAnyPCM(tokenEmail, confId))
        {
            throw new CMSForbidden("[ERROR] No rights to unbid from proposal");
        }
        this.proposalService.removeBid(confId, proposalId, email.getEmail());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{confId}/proposals/{proposalId}/sh")
    public void addSecondHandReviewer(@RequestHeader(name="Authorization") String token, @PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestBody JsonEmailDTO email)
    {
        String tokenEmail = this.userController.getEmailFromToken(token);
        if(!this.userService.isAnySCM(tokenEmail, confId))
        {
            throw new CMSForbidden("[ERROR] No rights to unbid from proposal");
        }
        this.proposalService.addSecondHandReviewer(confId, proposalId, email.getEmail());
    }

}
