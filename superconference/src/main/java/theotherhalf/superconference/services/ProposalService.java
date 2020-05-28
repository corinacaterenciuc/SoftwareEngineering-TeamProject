package theotherhalf.superconference.services;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theotherhalf.superconference.domain.*;
import theotherhalf.superconference.exceptions.ServiceException;
import theotherhalf.superconference.repository.ProposalsRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProposalService
{

    @Autowired
    private ProposalsRepository proposalsRepository;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private UserService userService;

    public ProposalService()
    {

    }

    public Proposal transactionalAddProposal(Conference conference, Proposal proposal)
    {
        return conference.addProposal(proposal);
    }

    @Transactional
    public void saveAsEntity(Proposal proposal, String email)
    {
        proposal.setAuthor(this.userService.getUserAfterValidation(email));
        this.proposalsRepository.save(proposal);
    }

    @Transactional
    public void addReviewersToProposal(Proposal proposal, Long conferenceId, List<String> reviewers)
    {
        if(null != reviewers)
        {
            if(reviewers.size() > 4)
            {
                throw new ServiceException("[ERROR] Too many reviewers given.");
            }
            Conference conference = this.conferenceService.getConferenceAfterValidation(conferenceId);
            List<CMSUser> users = this.userService.getUsersInEmailList(reviewers);
            List<CMSUser> pcm = new ArrayList<>();
            users.forEach(x -> {
                if(this.userService.hasRole(x, conference, ENUMERATION_ROLES.PCM) || this.userService.hasRole(x, conference, ENUMERATION_ROLES.CHAIR_PCM) || this.userService.hasRole(x, conference, ENUMERATION_ROLES.CO_CHAIR_PCM))
                {
                    pcm.add(x);
                }
            });
            proposal.setReviewers(pcm);
        }
    }

    @Transactional
    public void removeReviewersFromProposal(Proposal proposal, List<String> reviewerEmails)
    {
        if(null == reviewerEmails)
        {
            throw new ServiceException("[ERROR] Empty email list given to reviewers removal.");
        }
        proposal.removeReviewers(this.userService.getUsersInEmailList(reviewerEmails));

    }

    @Transactional
    public void addBiddersToProposal(Proposal proposal, Long conferenceId, List<String> bidders)
    {
        if(null != bidders)
        {
            Conference conference = this.conferenceService.getConferenceAfterValidation(conferenceId);
            List<CMSUser> users = this.userService.getUsersInEmailList(bidders);
            List<CMSUser> pcm = new ArrayList<>();
            users.forEach(x -> {
                if(this.userService.hasRole(x, conference, ENUMERATION_ROLES.PCM) || this.userService.hasRole(x, conference, ENUMERATION_ROLES.CHAIR_PCM) || this.userService.hasRole(x, conference, ENUMERATION_ROLES.CO_CHAIR_PCM))
                {
                    pcm.add(x);
                }
            });
            proposal.setReviewers(pcm);

            proposal.setBiddingPeople(pcm);
        }
    }

    @Transactional
    public void addCoAuthorsToProposal(Proposal proposal, List<String> coauthors)
    {
        if(null != coauthors)
        {
            proposal.setCoAuthors(this.userService.getUsersInEmailList(coauthors).stream().filter(x -> !x.getEmail().equals(proposal.getAuthor().getEmail())).collect(Collectors.toList()));
        }
    }

    @Transactional
    public void setFilePath(Long confId, Long proposalId, String filePath)
    {
        Proposal proposal = this.getProposal(confId, proposalId);
        proposal.setFilePath(filePath);
    }

    @Transactional
    public Proposal addConferenceProposal(Long confId, Proposal proposal)
    {
        List<CMSUser> authors = new ArrayList<>();
        List<CMSUser> bidrs = new ArrayList<>();
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);

        if (null == proposal.getID())
        {
            proposal.setID(new Random().nextLong());
        }

        Proposal response = this.transactionalAddProposal(conference, proposal);
        return response;
    }

    @Transactional
    public Proposal updateProposal(Long confId, Proposal proposal)
    {
        if (null == proposal.getID())
        {
            throw new ServiceException("[ERROR] Invalid proposal id given");
        }
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        return conference.updateProposal(proposal);
    }

    @Transactional
    public void removeProposal(Long confId, Long proposalId)
    {
        this.proposalsRepository.deleteById(proposalId);
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        // delete file from disk
        conference.removeProposal(proposalId);
    }

    public List<Proposal> getProposals(Long confId)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Section main = conference.getDefaultSection();
        return main.getProposals();

    }

    public Proposal getProposal(Long confId, Long proposalId)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Section main = conference.getDefaultSection();
        return main.getProposal(proposalId);
    }

    @Transactional
    public void addBid(Long confId, Long proposalId, String email)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Section main = conference.getDefaultSection();
        Proposal proposal = main.getProposal(proposalId);
        CMSUser usr = this.userService.getUserAfterValidation(email);
        proposal.addBidder(usr);
    }

    @Transactional
    public void removeBid(Long confId, Long proposalId, String email)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Section main = conference.getDefaultSection();
        Proposal proposal = main.getProposal(proposalId);
        CMSUser usr = this.userService.getUserAfterValidation(email);
        proposal.removeBidder(usr);
    }

    @Transactional
    public void addSecondHandReviewer(Long confId, Long proposalId, String email)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Section main = conference.getDefaultSection();
        Proposal proposal = main.getProposal(proposalId);
        CMSUser usr = this.userService.getUserAfterValidation(email);
        proposal.addSecondHandReviewer(usr);
    }

    @Transactional
    public void removeSecondHandReviewer(Long confId, Long proposalId, String email)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Section main = conference.getDefaultSection();
        Proposal proposal = main.getProposal(proposalId);
        CMSUser usr = this.userService.getUserAfterValidation(email);
        proposal.removeSecondHandReviewer(usr);
    }

    public List<CMSUser> getBidders(Long confId, Long proposalId)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Section main = conference.getDefaultSection();
        Proposal proposal = main.getProposal(proposalId);
        return proposal.getBiddingPeople();
    }

    public List<CMSUser> setReviewers(Long confId, Long proposalId, List<String> emails)
    {
        if(null == confId || null == proposalId)
        {
            throw new ServiceException("[ERROR] Invalid id provided");
        }
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        if(4 < emails.size())
        {
            throw new ServiceException("[ERROR] Too many reviewers given");
        }
        Section main = conference.getDefaultSection();
        Proposal proposal = main.getProposal(proposalId);
        List<CMSUser> userii = this.userService.getUsersInEmailList(emails);
        proposal.setReviewers(userii);
        return userii;
    }
}
