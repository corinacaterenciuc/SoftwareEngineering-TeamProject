package theotherhalf.superconference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.web.bind.annotation.*;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.User;
import theotherhalf.superconference.dto.ConferenceDTO;
import theotherhalf.superconference.dto.UserDTO;
import theotherhalf.superconference.exceptions.ControllerException;
import theotherhalf.superconference.services.ConferenceService;
import theotherhalf.superconference.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/conferences")
public class ConferenceController
{
    @Autowired
    private final ConferenceService conferenceService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService)
    {
        this.conferenceService = conferenceService;
    }

    public String getEmailFromJsonString(String jsonBody)
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

    @PostMapping
    public ConferenceDTO addConference(@RequestBody @Valid ConferenceDTO conferenceDTO)
    {
        Conference conference = ConferenceDTO.toDomain(conferenceDTO);
        Conference savedConference = this.conferenceService.add(conference);
        return ConferenceDTO.toDTO(savedConference);
    }

    @DeleteMapping
    public void deleteConference(@RequestBody String jsonBody)
    {
        JacksonJsonParser gson = new JacksonJsonParser();
        Long ID;
        try
        {
             ID = Long.parseLong(gson.parseMap(jsonBody).get("id").toString());
        }
        catch (Exception ex)
        {
            throw new ControllerException("[ERROR] Invalid delete conference request body");
        }

        this.conferenceService.delete(ID);
    }

    @GetMapping
    public List<ConferenceDTO> getAllConferences()
    {
        List<Conference> conferenceList = this.conferenceService.getAll();
        return conferenceList.stream().map(ConferenceDTO::toDTO).collect(Collectors.toList());
    }


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

}
