package theotherhalf.superconference.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.CMSUser;
import theotherhalf.superconference.domain.UserClaims;
import theotherhalf.superconference.dto.UserClaimsDTO;
import theotherhalf.superconference.dto.UserDTO;
import theotherhalf.superconference.exceptions.ControllerException;
import theotherhalf.superconference.services.ConferenceService;
import theotherhalf.superconference.services.FileService;
import theotherhalf.superconference.services.UserService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("api/users")
public class UserController
{
    private final UserService userService;
    private final ConferenceService conferenceService;

    @Autowired
    private FileService fileService;

    @Autowired
    public UserController(UserService userService, ConferenceService conferenceService)
    {
        this.userService = userService;
        this.conferenceService = conferenceService;
    }

    @PostMapping
    public UserDTO addNewUser(@RequestBody @Valid UserDTO userDTO)
    {
        CMSUser user = UserDTO.toDomain(userDTO);
        CMSUser savedUser = this.userService.addUser(user, userDTO.getPassword());
        return UserDTO.toDTO(savedUser);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteUser(@RequestParam("email") String email)
    {
        this.userService.deleteUser(email);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public UserDTO updateUser(@RequestBody @Valid UserDTO userDTO)
    {
        CMSUser user = UserDTO.toDomain(userDTO);
        CMSUser updatedUser = this.userService.updateUser(user);
        return UserDTO.toDTO(updatedUser);
    }

    @PutMapping(path="/admins")
    public void makeAdmin(@RequestBody String jsonBody)
    {
        JacksonJsonParser gson = new JacksonJsonParser();
        String eml = gson.parseMap(jsonBody).get("email").toString();
        this.userService.addAdmin(eml);
    }

    @DeleteMapping(path="/admins")
    public void deleteAdmin(@RequestBody String jsonBody)
    {
        JacksonJsonParser gson = new JacksonJsonParser();
        String eml = gson.parseMap(jsonBody).get("email").toString();
        this.userService.removeAdmin(eml);
    }

    @GetMapping
    public List<UserDTO> getAllUsers()
    {
        List<CMSUser> userList = this.userService.getAllUsers();
        return userList.stream().map(UserDTO::toDTO).collect(Collectors.toList());
    }

    @GetMapping(path="/user/{email}")
    public List<UserClaimsDTO> getAllRoles(@PathVariable("email") String userEmail)
    {
        CMSUser user = this.userService.findByEmail(userEmail).orElse(null);
        if (null == user)
        {
            throw new ControllerException("[ERROR] Null user given for roles search");
        }
        return this.userService.getAllRolesForUser(user).stream().map(UserClaimsDTO::toDTO).collect(Collectors.toList());
    }

}
