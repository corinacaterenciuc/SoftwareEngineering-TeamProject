package theotherhalf.superconference.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theotherhalf.superconference.domain.*;
import theotherhalf.superconference.exceptions.ServiceException;
import theotherhalf.superconference.repository.ConferenceRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class SectionService
{
    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ConferenceService conferenceService;

    public SectionService()
    {

    }

    private List<Proposal> getProposalsByKeys(Conference conference, List<ProposalKey> keys)
    {
        List<Proposal> allProposals = this.conferenceService.getConferenceProposals(conference.getID());
        return allProposals.stream().filter(x -> keys.stream().anyMatch(k -> k.getEmail().equals(x.getAuthor().getEmail()) && k.getTitle().equals(x.getProposalName()))).collect(Collectors.toList());
    }

    public Section addSection(Long confId, String chair, List<String> topics, List<ProposalKey> proposalKeys, List<String> emailParticipants, Integer room)
    {
        if(this.conferenceService.findById(confId).isEmpty())
        {
            throw new ServiceException("[ERROR] Conference doesn't exists!");
        }
        Conference conference = this.conferenceService.findById(confId).get();
        List<Proposal> proposals = this.getProposalsByKeys(conference, proposalKeys);
        if (this.userService.findByEmail(chair).isEmpty())
        {
            throw new ServiceException("[ERROR] User doesn't exist");
        }
        User userChair = this.userService.findByEmail(chair).get();
        List<User> participants = this.userService.getUsersInEmailList(emailParticipants);
        Section section = new Section(userChair, topics, proposals, participants, room);
        section.setID(new Random().nextLong());

        return conference.addSection(section);
    }

    public void updateSection(Long confId, String chair, List<String> topics, List<ProposalKey> proposalKeys, List<String> emailParticipants, Integer room)
    {
        if(this.conferenceService.findById(confId).isEmpty())
        {
            throw new ServiceException("[ERROR] Conference doesn't exists!");
        }
        Conference conference = this.conferenceService.findById(confId).get();
        List<Proposal> proposals = this.getProposalsByKeys(conference, proposalKeys);
        if (this.userService.findByEmail(chair).isEmpty())
        {
            throw new ServiceException("[ERROR] User doesn't exist");
        }
        User userChair = this.userService.findByEmail(chair).get();
        List<User> participants = this.userService.getUsersInEmailList(emailParticipants);
        Section section = new Section(userChair, topics, proposals, participants, room);
        conference.updateSection(section);
    }

    public void removeSection(Long confId, Long sectionId)
    {
        if(this.conferenceService.findById(confId).isEmpty())
        {
            throw new ServiceException("[ERROR] Conference doesn't exists!");
        }
        Conference conference = this.conferenceService.findById(confId).get();
        if(!conference.hasThisSection(sectionId))
        {
            throw new ServiceException("[ERROR} Conference doesn't have the given section");
        }
        conference.removeSection(sectionId);

    }

    public void addParticipants(Long confId, Long sectionId, String email)
    {
        Optional<Conference> conferenceOptional = this.conferenceService.findById(confId);
        if (conferenceOptional.isEmpty())
        {
            throw new ServiceException("[ERROR] Invalid conference id given");
        }
        Conference conference = conferenceOptional.get();
        if(!conference.hasThisSection(sectionId))
        {
            throw new ServiceException("[ERROR] Conference doesn't have the given section");
        }
        Section section = conference.getSectionById(sectionId);
        User usr = this.userService.getUserAfterValidation(email);
        section.addParticipant(usr);
    }

    public void removeParticipant(Long confId, Long sectionId, String email)
    {
        Optional<Conference> conferenceOptional = this.conferenceService.findById(confId);
        if (conferenceOptional.isEmpty())
        {
            throw new ServiceException("[ERROR] Invalid conference id given");
        }
        Conference conference = conferenceOptional.get();
        if(!conference.hasThisSection(sectionId))
        {
            throw new ServiceException("[ERROR] Conference doesn't have the given section");
        }
        Section section = conference.getSectionById(sectionId);
        User usr = this.userService.getUserAfterValidation(email);
        section.removeParticipant(usr);
    }

    public List<Section> getSections(Long confId)
    {
        Optional<Conference> conferenceOptional = this.conferenceService.findById(confId);
        if (conferenceOptional.isEmpty())
        {
            throw new ServiceException("[ERROR] Invalid conference id given");
        }
        Conference conference = conferenceOptional.get();
        return conference.getSections();
    }
}
