package theotherhalf.superconference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import theotherhalf.superconference.api.jwt.JWTokenUtil;
import theotherhalf.superconference.domain.CMSUser;
import theotherhalf.superconference.dto.UserClaimsDTO;
import theotherhalf.superconference.dto.UserDTO;
import theotherhalf.superconference.exceptions.CMSForbidden;
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
    private JWTokenUtil jwTokenUtil;

    @Autowired
    private FileService fileService;


    @Autowired
    public UserController(UserService userService, ConferenceService conferenceService)
    {
        this.userService = userService;
        this.conferenceService = conferenceService;
    }

    public String getEmailFromToken(String token)
    {
        String actualToken = token.split(" ")[1];
        String email = this.jwTokenUtil.getEmailFromToken(actualToken);
        return email;
    }

    @PostMapping
    public UserDTO addNewUser(@RequestHeader(name="Authorization") String token, @RequestBody @Valid UserDTO userDTO)
    {
        CMSUser user = UserDTO.toDomain(userDTO);
        CMSUser savedUser = this.userService.addUser(user);
        return UserDTO.toDTO(savedUser);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteUser(@RequestHeader(name="Authorization") String token, @RequestParam("email") String email)
    {
        String tokenEmail = this.getEmailFromToken(token);
        if(null == email)
        {
            throw new ControllerException("[ERROR] Null email given to delete");
        }
        if(!tokenEmail.equals(email))
        {
            throw new CMSForbidden("[ERROR] No rights to update user");
        }

        this.userService.deleteUser(email);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public UserDTO updateUser(@RequestHeader(name="Authorization") String token, @RequestBody @Valid UserDTO userDTO)
    {
        String tokenEmail = this.getEmailFromToken(token);

        if(null == userDTO.getEmail())
        {
            throw new ControllerException("[ERROR] Null email given to update");
        }
        if(!tokenEmail.equals(userDTO.getEmail()))
        {
            throw new CMSForbidden("[ERROR] No rights to update user");
        }

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
