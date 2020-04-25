package theotherhalf.superconference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.User;
import theotherhalf.superconference.domain.UserClaims;
import theotherhalf.superconference.dto.UserClaimsDTO;
import theotherhalf.superconference.dto.UserDTO;
import theotherhalf.superconference.services.ConferenceService;
import theotherhalf.superconference.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController
{
    private final UserService userService;
    private final ConferenceService conferenceService;

    @Autowired
    public UserController(UserService userService, ConferenceService conferenceService)
    {
        this.userService = userService;
        this.conferenceService = conferenceService;
    }

    @PostMapping
    public void addNewUser(@RequestBody @Valid UserDTO userDTO)
    {
        User user = UserDTO.toDomain(userDTO);
        this.userService.addUser(user);
    }

    @DeleteMapping(value = "{id}")
    public void deleteUser(@PathVariable("id") Long ID)
    {
        this.userService.deleteUser(ID);
    }

    @GetMapping
    public List<UserDTO> getAllUsers()
    {
        List<User> userList = this.userService.getAllUsers();
        return userList.stream().map(UserDTO::toDTO).collect(Collectors.toList());
    }

    @PutMapping(path="/scm/user={uid}&conf={cid}")
    public void addSCM(@PathVariable("uid") Long userID,@PathVariable("cid") Long conferenceID)
    {
        User user = this.userService.findById(userID).orElse(null);
        Conference conference = this.conferenceService.findById(conferenceID).orElse(null);
        if(null == user && null == conference)
        {
            // raise some error
            ;
        }
        this.userService.addSCM(user, conference);
    }

    @GetMapping(path="/user/{id}")
    public List<UserClaimsDTO> getAllRoles(@PathVariable("id") Long userID)
    {
        User user = this.userService.findById(userID).orElse(null);
        if (null == user)
        {
            //raise
            ;
        }
        return this.userService.getAllRolesForUser(user).stream().map(UserClaimsDTO::toDTO).collect(Collectors.toList());
    }
}
