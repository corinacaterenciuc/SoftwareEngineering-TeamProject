package otherhalf.superconference.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import otherhalf.superconference.dtos.UserCredentialsDTO;
import otherhalf.superconference.services.CMSUserService;

@RestController
@RequestMapping("/api/users")
public class CMSUserController
{
    private final CMSUserService cmsUserService;

    @Autowired
    public CMSUserController(CMSUserService cmsUserService)
    {
        this.cmsUserService = cmsUserService;
    }

    @PostMapping("/register")
    public void register(@RequestBody UserCredentialsDTO user)
    {

    }
}
