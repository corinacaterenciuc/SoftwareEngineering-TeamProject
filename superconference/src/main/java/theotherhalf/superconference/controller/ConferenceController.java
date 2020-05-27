package theotherhalf.superconference.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.web.bind.annotation.*;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.ProposalKey;
import theotherhalf.superconference.domain.User;
import theotherhalf.superconference.dto.ConferenceDTO;
import theotherhalf.superconference.dto.JsonEmailDTO;
import theotherhalf.superconference.dto.SectionDTO;
import theotherhalf.superconference.dto.UserDTO;
import theotherhalf.superconference.exceptions.ControllerException;
import theotherhalf.superconference.services.ConferenceService;
import theotherhalf.superconference.services.SectionService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("api/conferences")
public class ConferenceController
{
    @Autowired
    private final ConferenceService conferenceService;

    @Autowired
    private final SectionService sectionService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService, SectionService sectionService)
    {
        this.conferenceService = conferenceService;
        this.sectionService = sectionService;
    }

    private String getEmailFromJsonString(String jsonBody)
    {
        JacksonJsonParser gson = new JacksonJsonParser();
        String email;
        try
        {
            email = gson.parseMap(jsonBody).get("email").toString();
        }
        catch (Exception ex)
        {
            throw new ControllerException("[ERROR] Invalid request body email");
        }
        if(null == email || email.strip().equals(""))
        {
            throw new ControllerException("[ERROR] Invalid email");
        }
        return email;
    }
    public List<String> getEmailsFromJsonMailDTOs(List<JsonEmailDTO> mails)
    {
        return mails.stream().map(JsonEmailDTO::getEmail).collect(Collectors.toList());
    }

    public ConferenceDTO plugToConferenceDTOExtraBody(Conference conference)
    {
        ConferenceDTO response = ConferenceDTO.toDTO(conference);
        User cpcm = this.conferenceService.getConferenceCPCM(conference.getID());
        User ccpcm = this.conferenceService.getConferenceCCPCM(conference.getID());
        User cscm = this.conferenceService.getConferenceCSCM(conference.getID());
        User ccscm = this.conferenceService.getConferenceCCSCM(conference.getID());
        if(null != cpcm)
        {
            response.setCpcm(new JsonEmailDTO(cpcm.getEmail()));
        }
        if(null != ccpcm)
        {
            response.setCcpcm(new JsonEmailDTO(ccpcm.getEmail()));
        }
        if(null != cscm)
        {
            response.setCscm(new JsonEmailDTO(cscm.getEmail()));
        }
        if(null != ccscm)
        {
            response.setCcscm(new JsonEmailDTO(ccscm.getEmail()));
        }
        response.setPcms(this.conferenceService.getConferencePCM(conference.getID()).stream().map(x -> new JsonEmailDTO(x.getEmail())).collect(Collectors.toList()));
        response.setScms(this.conferenceService.getConferenceSCM(conference.getID()).stream().map(x -> new JsonEmailDTO(x.getEmail())).collect(Collectors.toList()));
        response.setParticipants(this.conferenceService.getParticipants(conference.getID()).stream().map(x -> new JsonEmailDTO(x.getEmail())).collect(Collectors.toList()));

        if(this.sectionService.getSections(conference.getID()) != null)
        {
            response.setSections(this.sectionService.getSections(conference.getID()).stream().map(x -> SectionDTO.toDTO(conference.getID(), x)).collect(Collectors.toList()));
        }
        return response;
    }

    public void processConferenceDTOExtraBody(ConferenceDTO conferenceDTO, Conference conference)
    {
        if(null != conferenceDTO.getParticipants())
        {
            List<String> participants = this.getEmailsFromJsonMailDTOs(conferenceDTO.getParticipants());
            participants.forEach(x -> this.conferenceService.addParticipantToConference(conference.getID(), x));
        }

        if(null != conferenceDTO.getPcms())
        {
            this.conferenceService.makePCMS(conference.getID(), this.getEmailsFromJsonMailDTOs(conferenceDTO.getPcms()));
        }
        if(null != conferenceDTO.getScms())
        {
            this.conferenceService.makeSCMS(conference.getID(), this.getEmailsFromJsonMailDTOs(conferenceDTO.getScms()));
        }
        if(null != conferenceDTO.getCpcm())
        {
            this.conferenceService.addCPCM(conferenceDTO.getCpcm().getEmail(), conference.getID());
        }
        if(null != conferenceDTO.getCcpcm())
        {
            this.conferenceService.addCCPCM(conferenceDTO.getCcpcm().getEmail(), conference.getID());
        }
        if(null != conferenceDTO.getCscm())
        {
            this.conferenceService.addCSCM(conferenceDTO.getCscm().getEmail(),conference.getID());
        }
        if(null != conferenceDTO.getCcscm())
        {
            this.conferenceService.addCCSCM(conferenceDTO.getCcscm().getEmail(), conference.getID());
        }
    }
    @Transactional
    @PostMapping
    public ConferenceDTO addConference(@RequestBody @Valid ConferenceDTO conferenceDTO)
    {
        Conference conference = ConferenceDTO.toDomain(conferenceDTO);
        Conference savedConference = this.conferenceService.add(conference);

        this.processConferenceDTOExtraBody(conferenceDTO, savedConference);
        ConferenceDTO response = this.plugToConferenceDTOExtraBody(savedConference);
        return response;
    }

    @Transactional
    @PutMapping
    public ConferenceDTO updateConference(@RequestBody @Valid ConferenceDTO conferenceDTO)
    {
        if(null == conferenceDTO)
        {
            throw new ControllerException("[ERROR] No conference id given for update");
        }
        Conference conference = ConferenceDTO.toDomain(conferenceDTO);
        conference.setID(conferenceDTO.getId());
        Conference savedConference = this.conferenceService.update(conference);

        this.processConferenceDTOExtraBody(conferenceDTO, savedConference);
        ConferenceDTO response = this.plugToConferenceDTOExtraBody(savedConference);
        return response;
    }

    @DeleteMapping
    public void deleteConference(@RequestParam("id") Long confId)
    {
        this.conferenceService.delete(confId); // fuck
    }

    @GetMapping
    public List<ConferenceDTO> getAllConferences()
    {
        List<Conference> conferenceList = this.conferenceService.getAll();
        return conferenceList.stream().map(this::plugToConferenceDTOExtraBody).collect(Collectors.toList());
    }

    // ------------ USERS -----------
    @GetMapping(path = "{confId}/pcm")
    public List<UserDTO> getAllPCM(@PathVariable("confId") Long confId)
    {
        List<User> usersPCM = this.conferenceService.getConferencePCM(confId);
        return usersPCM.stream().map(UserDTO::toDTO).collect(Collectors.toList());
    }

    @GetMapping(path = "{confId}/scm")
    public List<UserDTO> getAllSCM(@PathVariable("confId") Long confId)
    {
        List<User> usersPCM = this.conferenceService.getConferenceSCM(confId);
        return usersPCM.stream().map(UserDTO::toDTO).collect(Collectors.toList());
    }

    @PutMapping(path = "{confId}/scm")
    public void addSCM(@PathVariable("confId") Long confId, @RequestBody String jsonBody)
    {
        String userEmail = this.getEmailFromJsonString(jsonBody);
        this.conferenceService.addSCM(userEmail, confId);
    }

    @DeleteMapping(path = "{confId}/scm")
    public void removeSCM(@PathVariable("confId") Long confId, @RequestBody String jsonBody)
    {
        String userEmail = this.getEmailFromJsonString(jsonBody);
        this.conferenceService.removeSCM(userEmail, confId);
    }

    @PutMapping(path = "{confId}/cscm")
    public void addCSCM(@PathVariable("confId") Long confId, @RequestBody String jsonBody)
    {
        String userEmail = this.getEmailFromJsonString(jsonBody);
        this.conferenceService.addCSCM(userEmail, confId);
    }

    @DeleteMapping(path = "{confId}/cscm")
    public void removeCSCM(@PathVariable("confId") Long confId, @RequestBody String jsonBody)
    {
        String userEmail = this.getEmailFromJsonString(jsonBody);
        this.conferenceService.removeCSCM(userEmail, confId);
    }

    @PutMapping(path = "{confId}/ccscm")
    public void addCCSCM(@PathVariable("confId") Long confId, @RequestBody String jsonBody)
    {
        String userEmail = this.getEmailFromJsonString(jsonBody);
        this.conferenceService.addCCSCM(userEmail, confId);
    }

    @DeleteMapping(path = "{confId}/ccscm")
    public void removeCCSCM(@PathVariable("confId") Long confId, @RequestBody String jsonBody)
    {
        String userEmail = this.getEmailFromJsonString(jsonBody);
        this.conferenceService.removeCCSCM(userEmail, confId);
    }

    @PutMapping(path = "{confId}/pcm")
    public void addPCM(@PathVariable("confId") Long confId, @RequestBody String jsonBody)
    {
        String userEmail = this.getEmailFromJsonString(jsonBody);
        this.conferenceService.addPCM(userEmail, confId);
    }

    @DeleteMapping(path = "{confId}/pcm")
    public void removePCM(@PathVariable("confId") Long confId, @RequestBody String jsonBody)
    {
        String userEmail = this.getEmailFromJsonString(jsonBody);
        this.conferenceService.removePCM(userEmail, confId);
    }

    @PutMapping(path = "{confId}/cpcm")
    public void addCPCM(@PathVariable("confId") Long confId, @RequestBody String jsonBody)
    {
        String userEmail = this.getEmailFromJsonString(jsonBody);
        this.conferenceService.addCPCM(userEmail, confId);
    }

    @DeleteMapping(path = "{confId}/cpcm")
    public void removeCPCM(@PathVariable("confId") Long confId, @RequestBody String jsonBody)
    {
        String userEmail = this.getEmailFromJsonString(jsonBody);
        this.conferenceService.removeCPCM(userEmail, confId);
    }

    @PutMapping(path = "{confId}/ccpcm")
    public void addCCPCM(@PathVariable("confId") Long confId, @RequestBody String jsonBody)
    {
        String userEmail = this.getEmailFromJsonString(jsonBody);
        this.conferenceService.addCCPCM(userEmail, confId);
    }

    @DeleteMapping(path = "{confId}/ccpcm")
    public void removeCCPCM(@PathVariable("confId") Long confId, @RequestBody String jsonBody)
    {
        String userEmail = this.getEmailFromJsonString(jsonBody);
        this.conferenceService.removeCCPCM(userEmail, confId);
    }

    // ------ SECTIONS --------


    @PutMapping(path = "{confId}/participants")
    public void addParticipantToConference(@PathVariable("confId") Long confId, @RequestBody String jsonBody)
    {
        String userEmail = this.getEmailFromJsonString(jsonBody);
        this.conferenceService.addParticipantToConference(confId, userEmail);
    }

    @DeleteMapping(path = "{confId}/participants")
    public void removeParticipantFromConference(@PathVariable("confId") Long confId, @RequestParam("email") String email)
    {
        this.conferenceService.removeParticipantFromConference(confId, email);
    }


}
