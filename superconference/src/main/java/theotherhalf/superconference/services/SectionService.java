package theotherhalf.superconference.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theotherhalf.superconference.domain.*;
import theotherhalf.superconference.exceptions.ServiceException;
import theotherhalf.superconference.repository.ConferenceRepository;
import theotherhalf.superconference.repository.SectionRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
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

    @Autowired
    private SectionRepository sectionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public SectionService()
    {

    }

    private List<Proposal> getProposalsByKeys(Conference conference, List<ProposalKey> keys)
    {
        List<Proposal> allProposals = this.conferenceService.getConferenceProposals(conference.getID());
        return allProposals.stream().filter(x -> keys.stream().anyMatch(k -> k.getEmail().equals(x.getAuthor().getEmail()) && k.getTitle().equals(x.getProposalName()))).collect(Collectors.toList());
    }

    @Transactional
    public Section saveEntity(CMSUser user, List<String> topics, List<CMSUser> participants, List<Proposal> proposals, Integer room)
    {
        Section section = new Section(user, topics, proposals, participants, room);
        section.setId(new Random().nextLong());
        //section.setID(new Random().nextLong());
        //this.sectionRepository.save(section);

        return section;
    }

    public Section addSection(Long confId, String chair, List<String> topics, List<ProposalKey> proposalKeys, List<String> emailParticipants, Integer room)
    {
        //Section section = this.saveEntity();
        //return this.conferenceService.addSectionToConference(confId);

        if(this.conferenceService.findById(confId).isEmpty())
        {
            throw new ServiceException("[ERROR] Conference doesn't exists!");
        }
        Conference conference = this.conferenceService.findById(confId).get();
        List<Proposal> proposals = this.getProposalsByKeys(conference, proposalKeys);
        if (this.userService.findByEmail(chair).isEmpty())
        {
            throw new ServiceException("[ERROR] CMSUser doesn't exist");
        }
        CMSUser userChair = this.userService.findByEmail(chair).get();
        List<CMSUser> participants = this.userService.getUsersInEmailList(emailParticipants);
//        Section section = new Section(userChair, topics, proposals, participants, room);
//        section.setID(new Random().nextLong());
        Section saved = this.saveEntity(userChair, topics, participants, proposals, room);
        return this.conferenceService.addSectionToConference(confId, saved);
    }

    @Transactional
    public void updateSection(Long id, Long confId, String chair, List<String> topics, List<ProposalKey> proposalKeys, List<String> emailParticipants, Integer room)
    {
        if(null == id)
        {
            throw new ServiceException("[ERROR] Null id given to update");
        }
        CMSUser userChair = null;
        if(this.conferenceService.findById(confId).isEmpty())
        {
            throw new ServiceException("[ERROR] Conference doesn't exists!");
        }
        Conference conference = this.conferenceService.findById(confId).get();
        List<Proposal> proposals = this.getProposalsByKeys(conference, proposalKeys);
        if (null != chair && this.userService.findByEmail(chair).isEmpty())
        {
            throw new ServiceException("[ERROR] CMSUser doesn't exist");
        }
        if(null != chair)
        {
            userChair = this.userService.findByEmail(chair).get();
        }
        List<CMSUser> participants = this.userService.getUsersInEmailList(emailParticipants);
        Section section = new Section(userChair, topics, proposals, participants, room);
        section.setId(id);
        conference.updateSection(section);
    }

    @Transactional
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

    @Transactional
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
        CMSUser usr = this.userService.getUserAfterValidation(email);
        section.addParticipant(usr);
    }

    @Transactional
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
        CMSUser usr = this.userService.getUserAfterValidation(email);
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
