package theotherhalf.superconference.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.stereotype.Service;
import theotherhalf.superconference.domain.*;
import theotherhalf.superconference.exceptions.ServiceException;
import theotherhalf.superconference.repository.ConferenceRepository;
import theotherhalf.superconference.repository.RoleRepository;
import theotherhalf.superconference.repository.SectionRepository;
import theotherhalf.superconference.validators.ConferenceValidator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class ConferenceService
{
    @Autowired
    private ConferenceRepository repository;

    @Autowired
    private ConferenceValidator conferenceValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private SectionRepository sectionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ConferenceService()
    {

    }

    @Transactional
    public Conference add(Conference conference)
    {
        if (null == conference.getID())
        {
            conference.setID(new Random().nextLong());
        }
        this.conferenceValidator.validate(conference);
        return this.repository.save(conference);
    }

    public void delete(Long ID)
    {
        if (null == ID)
        {
            throw new ServiceException("[ERROR] Null id given for removal");
        }
        Conference conference = this.getConferenceAfterValidation(ID);
        this.userService.deleteAllClaimsFromConference(conference);
        this.repository.deleteById(ID);
    }

    @Transactional
    public Conference update(Conference sameConference)
    {
        if(null == sameConference)
        {
            throw new ServiceException("[ERROR] Null Conference given to update");
        }
        Optional<Conference> tempConference = this.repository.findById(sameConference.getID());
        if(tempConference.isEmpty())
        {
            throw new ServiceException("[ERROR] Conference doesn't exist.");
        }
        Conference repoConference = tempConference.get();

        Conference dummyConference = new Conference();
        dummyConference.setName(repoConference.getName());
        dummyConference.setDescription(repoConference.getDescription());
        dummyConference.setAbstractDeadline(repoConference.getAbstractDeadline());
        dummyConference.setBiddingDeadline(repoConference.getBiddingDeadline());
        dummyConference.setPresentationDeadline(repoConference.getPresentationDeadline());
        dummyConference.setEvaluationDeadline(repoConference.getEvaluationDeadline());
        dummyConference.setProposalDeadline(repoConference.getProposalDeadline());
        dummyConference.setZeroDeadline(repoConference.getZeroDeadline());

        if(null != sameConference.getName() && ! sameConference.getName().strip().equals(""))
        {
            dummyConference.setName(sameConference.getName());
        }
        if(null != sameConference.getDescription() && ! sameConference.getDescription().strip().equals(""))
        {
            dummyConference.setDescription(sameConference.getDescription());
        }
        if(null != sameConference.getZeroDeadline())
        {
            dummyConference.setZeroDeadline(sameConference.getZeroDeadline());
        }
        if(null != sameConference.getAbstractDeadline())
        {
            dummyConference.setAbstractDeadline(sameConference.getAbstractDeadline());
        }
        if(null != sameConference.getBiddingDeadline())
        {
            dummyConference.setBiddingDeadline(sameConference.getBiddingDeadline());
        }
        if(null != sameConference.getProposalDeadline())
        {
            dummyConference.setProposalDeadline(sameConference.getProposalDeadline());
        }
        if(null != sameConference.getPresentationDeadline())
        {
            dummyConference.setPresentationDeadline(sameConference.getPresentationDeadline());
        }
        if(null != sameConference.getEvaluationDeadline())
        {
            dummyConference.setEvaluationDeadline(sameConference.getEvaluationDeadline());
        }
        this.conferenceValidator.validate(dummyConference);

        repoConference.setName(dummyConference.getName());
        repoConference.setDescription(dummyConference.getDescription());
        repoConference.setZeroDeadline(dummyConference.getZeroDeadline());
        repoConference.setAbstractDeadline(dummyConference.getAbstractDeadline());
        repoConference.setProposalDeadline(dummyConference.getProposalDeadline());
        repoConference.setBiddingDeadline(dummyConference.getBiddingDeadline());
        repoConference.setEvaluationDeadline(dummyConference.getEvaluationDeadline());
        repoConference.setPresentationDeadline(dummyConference.getPresentationDeadline());
        return repoConference;

    }

    public List<Conference> getAll()
    {
        return this.repository.findAll();
    }

    public Conference getConferenceAfterValidation(Long conferenceID)
    {
        Optional<Conference> tempConf = this.findById(conferenceID);
        if(tempConf.isEmpty())
        {
            throw new ServiceException("[ERROR] Invalid conference ID");
        }
        return tempConf.get();
    }

    @Transactional
    public Section addSectionToConference(Long conferenceId, Section section)
    {
        //section.setID(new Random().nextLong());
        Conference conference = this.getConferenceAfterValidation(conferenceId);
        conference.addSection(section);
        return section;
    }
    public Optional<Conference> findById(Long conferenceID)
    {
        return this.repository.findById(conferenceID);
    }

    public List<User> getConferenceSCM(Long conferenceID)
    {
        Conference conference = this.getConferenceAfterValidation(conferenceID);
        return this.userService.getConferenceSCM(conference);
    }

    public List<User> getConferencePCM(Long conferenceID)
    {
        Conference conference = this.getConferenceAfterValidation(conferenceID);
        return this.userService.getConferencePCM(conference);
    }

    public User getConferenceCPCM(Long conferenceId)
    {
        Conference conference = this.getConferenceAfterValidation(conferenceId);
        return this.userService.getConferenceCPCM(conference);
    }

    public User getConferenceCCPCM(Long conferenceId)
    {
        Conference conference = this.getConferenceAfterValidation(conferenceId);
        return this.userService.getConferenceCCPCM(conference);
    }

    public User getConferenceCSCM(Long conferenceId)
    {
        Conference conference = this.getConferenceAfterValidation(conferenceId);
        return this.userService.getConferenceCSCM(conference);
    }

    public User getConferenceCCSCM(Long conferenceId)
    {
        Conference conference = this.getConferenceAfterValidation(conferenceId);
        return this.userService.getConferenceCCSCM(conference);
    }

    @Transactional
    public void makeSCMS(Long conferenceId, List<String> emails)
    {
        Conference conference = this.getConferenceAfterValidation(conferenceId);
        emails.forEach(x -> this.userService.addSCM(x, conference));
    }

    @Transactional
    public void makePCMS(Long conferenceId, List<String> emails)
    {
        emails.forEach(x -> this.addPCM(x, conferenceId));
    }

    @Transactional
    public void addSCM(String userEmail, Long conferenceID)
    {
        this.userService.addSCM(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    public void removeSCM(String userEmail, Long conferenceID)
    {
        this.userService.removeSCM(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    public void addCSCM(String userEmail, Long conferenceID)
    {
        this.userService.addCSCM(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    public void removeCSCM(String userEmail, Long conferenceID)
    {
        this.userService.removeCSCM(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    public void addCCSCM(String userEmail, Long conferenceID)
    {
        this.userService.addCCSCM(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    public void removeCCSCM(String userEmail, Long conferenceID)
    {
        this.userService.removeCCSCM(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    @Transactional
    public void addPCM(String userEmail, Long conferenceID)
    {
        this.userService.addPCM(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    public void removePCM(String userEmail, Long conferenceID)
    {
        this.userService.removePCM(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    public void addCPCM(String userEmail, Long conferenceID)
    {
        this.userService.addCPCM(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    public void removeCPCM(String userEmail, Long conferenceID)
    {
        this.userService.removeCPCM(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    public void addCCPCM(String userEmail, Long conferenceID)
    {
        this.userService.addCCPCM(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    public void removeCCPCM(String userEmail, Long conferenceID)
    {
        this.userService.removeCCPCM(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    public void addSectionChair(String userEmail, Long conferenceID)
    {
        this.userService.addSectionChair(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    public void removeSectionChair(String userEmail, Long conferenceID)
    {
        this.userService.removeSectionChair(userEmail, this.getConferenceAfterValidation(conferenceID));
    }

    public List<User> getParticipants(Long conferenceId)
    {
        Conference conference = this.getConferenceAfterValidation(conferenceId);
        return conference.getParticipants();
    }
    public List<Proposal> getConferenceProposals(Long confId)
    {
        if(this.repository.findById(confId).isEmpty())
        {
            throw new ServiceException("[ERROR] Conference doesn't exists!");
        }
        Conference conference = this.repository.findById(confId).get();
        List<Section> sections = conference.getSections();
        ArrayList<Proposal> allProposals = new ArrayList<>();
        if(null != sections)
        {
            sections.forEach( x -> allProposals.addAll(x.getProposals()));
        }
        return allProposals;
    }



    public void addParticipantToConference(Long confId, String email)
    {
        if(this.repository.findById(confId).isEmpty())
        {
            throw new ServiceException("[ERROR] Conference doesn't exists!");
        }
        Conference conference = this.repository.findById(confId).get();
        User usr = this.userService.getUserAfterValidation(email);
        conference.addParticipantToConference(usr);
    }

    public void removeParticipantFromConference(Long confId, String email)
    {
        if(this.repository.findById(confId).isEmpty())
        {
            throw new ServiceException("[ERROR] Conference doesn't exists!");
        }
        Conference conference = this.repository.findById(confId).get();
        User usr = this.userService.getUserAfterValidation(email);
        conference.removeParticipantFromConference(usr);
    }
}
