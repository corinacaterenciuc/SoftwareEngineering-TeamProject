package theotherhalf.superconference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.User;
import theotherhalf.superconference.dto.ConferenceDTO;
import theotherhalf.superconference.dto.UserDTO;
import theotherhalf.superconference.services.ConferenceService;
import theotherhalf.superconference.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/conferences")
public class ConferenceController
{
    private final ConferenceService conferenceService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService)
    {
        this.conferenceService = conferenceService;
    }

    @PostMapping
    public void addNewConference(@RequestBody @Valid ConferenceDTO conferenceDTO)
    {
        Conference conference = ConferenceDTO.toDomain(conferenceDTO);
        this.conferenceService.add(conference);
    }

    @DeleteMapping(value = "{id}")
    public void deleteUser(@PathVariable("id") Long ID)
    {
        this.conferenceService.delete(ID);
    }

    @GetMapping
    public List<ConferenceDTO> getAllUsers()
    {
        List<Conference> userList = this.conferenceService.getAll();
        return userList.stream().map(ConferenceDTO::toDTO).collect(Collectors.toList());
    }
}
