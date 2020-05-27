package theotherhalf.superconference.services;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.Proposal;
import theotherhalf.superconference.domain.Section;
import theotherhalf.superconference.domain.User;
import theotherhalf.superconference.exceptions.ServiceException;
import theotherhalf.superconference.repository.ProposalsRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    public void addReviewersToProposal(Proposal proposal, List<String> reviewers)
    {
        if(null != reviewers)
        {
            if(reviewers.size() > 4)
            {
                throw new ServiceException("[ERROR] Too many reviewers given.");
            }
            proposal.setReviewers(this.userService.getUsersInEmailList(reviewers));
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
    public void addBiddersToProposal(Proposal proposal, List<String> bidders)
    {
        if(null != bidders)
        {
            proposal.setBiddingPeople(this.userService.getUsersInEmailList(bidders));
        }
    }

    @Transactional
    public void addCoAuthorsToProposal(Proposal proposal, List<String> coauthors)
    {
        if(null != coauthors)
        {
            proposal.setCoAuthors(this.userService.getUsersInEmailList(coauthors));
        }
    }

    @Transactional
    public Proposal addConferenceProposal(Long confId, Proposal proposal)
    {
        List<User> authors = new ArrayList<>();
        List<User> bidrs = new ArrayList<>();
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);

        if (null == proposal.getID())
        {
            proposal.setID(new Random().nextLong());
        }

        Proposal response = this.transactionalAddProposal(conference, proposal);

        // here save file on disk and give a name
        return response;
    }

    public void updateProposal(Long confId, Proposal proposal)
    {
        if (null == proposal.getID())
        {
            throw new ServiceException("[ERROR] Invalid proposal id given");
        }
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        // process file if present and retrieve just path
        conference.updateProposal(proposal);
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

    @Transactional
    public void addBid(Long confId, Long proposalId, String email)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Section main = conference.getDefaultSection();
        Proposal proposal = main.getProposal(proposalId);
        User usr = this.userService.getUserAfterValidation(email);
        proposal.addBidder(usr);
    }

    @Transactional
    public void removeBid(Long confId, Long proposalId, String email)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Section main = conference.getDefaultSection();
        Proposal proposal = main.getProposal(proposalId);
        User usr = this.userService.getUserAfterValidation(email);
        proposal.removeBidder(usr);
    }

    public List<User> getBidders(Long confId, Long proposalId)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        Section main = conference.getDefaultSection();
        Proposal proposal = main.getProposal(proposalId);
        return proposal.getBiddingPeople();
    }

    public List<User> setReviewers(Long confId, Long proposalId, List<String> emails)
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
        List<User> userii = this.userService.getUsersInEmailList(emails);
        proposal.setReviewers(userii);
        return userii;
    }
}
