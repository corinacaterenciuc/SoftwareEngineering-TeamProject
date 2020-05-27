package theotherhalf.superconference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.web.bind.annotation.*;
import theotherhalf.superconference.domain.ProposalKey;
import theotherhalf.superconference.dto.SectionDTO;
import theotherhalf.superconference.exceptions.ControllerException;
import theotherhalf.superconference.services.ConferenceService;
import theotherhalf.superconference.services.SectionService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RestController
@RequestMapping("api/conferences")
public class SectionController
{

    @Autowired
    private final ConferenceService conferenceService;

    @Autowired
    private final SectionService sectionService;


    public SectionController(ConferenceService conferenceService, SectionService sectionService) {
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

    @PostMapping(path = "{confId}/sections")
    public SectionDTO addSection(@PathVariable("confId") Long confId, @RequestBody SectionDTO sectionDTO)
    {
        List<ProposalKey> proposalKeys = new ArrayList<>();
        List<String> participantsEmail = new ArrayList<>();
        if(sectionDTO.getProposals() != null)
        {
            sectionDTO.getProposals().forEach(x -> proposalKeys.add(new ProposalKey(x.getAuthor().getEmail(), x.getProposalName(), confId)));
        }
        if(sectionDTO.getParticipants() != null)
        {
            sectionDTO.getParticipants().forEach(x -> participantsEmail.add(x.getEmail()));
        }
        this.sectionService.addSection(confId, sectionDTO.getChair(), sectionDTO.getTopics(), proposalKeys, participantsEmail, sectionDTO.getRoom());
        return sectionDTO;
    }

    @PutMapping(path = "{confId}/sections")
    public void updateSection(@PathVariable("confId") Long confId, @RequestBody SectionDTO sectionDTO)
    {
        List<ProposalKey> proposalKeys = new ArrayList<>();
        List<String> participantsEmail = new ArrayList<>();
        if(sectionDTO.getProposals() != null)
        {
            sectionDTO.getProposals().forEach(x -> proposalKeys.add(new ProposalKey(x.getAuthor().getEmail(), x.getProposalName(), confId)));
        }
        if(sectionDTO.getParticipants() != null)
        {
            sectionDTO.getParticipants().forEach(x -> participantsEmail.add(x.getEmail()));
        }
        this.sectionService.updateSection(confId, sectionDTO.getChair(), sectionDTO.getTopics(), proposalKeys, participantsEmail, sectionDTO.getRoom());
    }

    @DeleteMapping(path = "{confId}/sections")
    public void removeSection(@PathVariable("confId") Long confId, @RequestParam("id") Long sectionId)
    {
        System.out.println("FICUS");
    }


    @PutMapping(path = "{confId}/sections/{sectionId}/participants")
    public void addParticipantToSection(@PathVariable("confId") Long confId, @PathVariable("sectionId") Long sectionId, @RequestBody String jsonString)
    {
        String email = this.getEmailFromJsonString(jsonString);
        this.sectionService.addParticipants(confId, sectionId, email);
    }

    @DeleteMapping(path = "{confId}/sections/{sectionId}/participants")
    public void removeParticipantFromSection(@PathVariable("confId") Long confId, @PathVariable("sectionId") Long sectionId, @RequestParam("email") String email)
    {
        this.sectionService.removeParticipant(confId, sectionId, email);
    }
}
