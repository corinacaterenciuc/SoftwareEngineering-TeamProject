package otherhalf.superconference.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CMSUserService
{
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CMSUserService(BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
}
