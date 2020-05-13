package theotherhalf.superconference.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.ENUMERATION_ROLES;
import theotherhalf.superconference.domain.User;
import theotherhalf.superconference.domain.UserClaims;
import theotherhalf.superconference.exceptions.ConferenceManagementSystemException;
import theotherhalf.superconference.exceptions.ValidationException;
import theotherhalf.superconference.repository.RoleRepository;
import theotherhalf.superconference.repository.UserRepository;
import theotherhalf.superconference.validators.UserValidator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService
{
    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserValidator userValidator;

    public boolean addUser(User user) throws ConferenceManagementSystemException
    {
        if (this.repository.findById(user.getEmail()).isPresent())
        {
            throw new ConferenceManagementSystemException("User already exists");
        }
        this.userValidator.validate(user);
        this.repository.save(user);
        return true;
    }

    public boolean deleteUser(String email)
    {
        this.repository.deleteById(email);
        return true;
    }

/*    @Transactional
    public boolean updateUser(Long ID, User newUser)
    {
        User toUpdate = this.repository.findById(ID).orElse(null);
        if(null == toUpdate)
        {
            return false;
        }
        toUpdate.setID(newUser.getID());
        toUpdate.setEmail(newUser.getEmail());
        toUpdate.setFirst_name(newUser.getFirst_name());
        toUpdate.setLast_name(newUser.getLast_name());
        toUpdate.setPassword(newUser.getPassword());
        return true;
    }*/

    public List<User> getAllUsers()
    {
        return this.repository.findAll();
    }

    public Optional<User> findByEmail(String email)
    {
        return this.repository.findById(email);
    }

    public void addSCM(User user, Conference conference)
    {
        UserClaims role = new UserClaims(user, conference, ENUMERATION_ROLES.SCM);
        this.roleRepository.save(role);
    }

    public List<UserClaims> getAllRolesForUser(User user)
    {
        List<UserClaims> claimsList = this.roleRepository.findAll().stream().filter( x-> x.getUser().getEmail().equals(user.getEmail())).collect(Collectors.toList());
        return claimsList;
    }
}
