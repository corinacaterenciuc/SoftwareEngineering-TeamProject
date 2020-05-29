package theotherhalf.superconference.api.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import theotherhalf.superconference.domain.CMSUser;
import theotherhalf.superconference.dto.UserDTO;
import theotherhalf.superconference.repository.UserRepository;
import theotherhalf.superconference.services.UserService;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<CMSUser> user = userDao.findByEmail(username);
        if (user.isEmpty())
        {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        CMSUser goodUser = user.get();
        return new org.springframework.security.core.userdetails.User(goodUser.getEmail(), goodUser.getPassword(),
                new ArrayList<>());
    }

    public UserDTO getUserFromEmail(String email)
    {
        return UserDTO.toDTO(this.userService.getUserAfterValidation(email));
    }

    public CMSUser save(UserDTO user)
    {
        CMSUser newUser = new CMSUser();
        newUser.setFirstName(user.getfirstName());
        newUser.setLastName(user.getlastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return this.userService.addUser(newUser);
        //return userDao.save(newUser);
    }
}