package theotherhalf.superconference.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theotherhalf.superconference.domain.*;
import theotherhalf.superconference.exceptions.ConferenceManagementSystemException;
import theotherhalf.superconference.exceptions.ServiceException;
import theotherhalf.superconference.exceptions.ValidationException;
import theotherhalf.superconference.repository.AdminRepository;
import theotherhalf.superconference.repository.RoleRepository;
import theotherhalf.superconference.repository.UserRepository;
import theotherhalf.superconference.validators.UserValidator;

import javax.sql.rowset.serial.SerialException;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
    private AdminRepository adminRepository;

    @Autowired
    private UserValidator userValidator;


    public User addUser(User user) throws ConferenceManagementSystemException
    {
        if (this.repository.findById(user.getEmail()).isPresent())
        {
            throw new ServiceException("[ERROR] User already exists");
        }
        this.userValidator.validate(user);
        return this.repository.save(user);
    }

    public boolean deleteUser(String email)
    {
        if(null == email)
        {
            throw new ServiceException("[ERROR] Null id given for removal");
        }
        this.repository.deleteById(email);
        return true;
    }

    @Transactional
    public User updateUser(User sameUser)
    {
        if(null == sameUser)
        {
            throw new ServiceException("[ERROR] Null User given to update");
        }
        Optional<User> tempUser = this.repository.findById(sameUser.getEmail());
        if(tempUser.isEmpty())
        {
            throw new ServiceException("[ERROR] User doesn't exist.");
        }
        User repoUser = tempUser.get();
        User dummyUser = new User();
        dummyUser.setEmail(repoUser.getEmail());
        dummyUser.setPassword(repoUser.getPassword());
        dummyUser.setLastName(repoUser.getLastName());
        dummyUser.setFirstName(repoUser.getFirstName());

        if(sameUser.getFirstName() != null && ! sameUser.getFirstName().strip().equals(""))
        {
            dummyUser.setFirstName(sameUser.getFirstName());
        }
        if(sameUser.getLastName() != null && ! sameUser.getLastName().strip().equals(""))
        {
            dummyUser.setLastName(sameUser.getLastName());
        }
        if(sameUser.getPassword() != null)
        {
            dummyUser.setPassword(sameUser.getPassword());
        }
        this.userValidator.validate(dummyUser);

        repoUser.setFirstName(dummyUser.getFirstName());
        repoUser.setLastName(dummyUser.getLastName());
        repoUser.setPassword(dummyUser.getPassword());
        repoUser.setEmail(dummyUser.getEmail());
        return repoUser;
    }

    public List<User> getAllUsers()
    {
        return this.repository.findAll();
    }

    public Optional<User> findByEmail(String email)
    {
        return this.repository.findById(email);
    }

    public User getUserAfterValidation(String email)
    {
        if(null == email)
        {
            throw new ServiceException("[ERROR] Null email address given");
        }
        Optional<User> opUser = this.repository.findById(email);
        if(opUser.isEmpty())
        {
            throw new ServiceException("[ERROR] Invalid email address given");
        }
        return opUser.get();
    }

    public List<User> getConferenceUsersByRole(Conference conference, ENUMERATION_ROLES role)
    {
        List<UserClaims> userClaimsPCM = this.roleRepository.findByConferenceAndRole(conference, role);
        List<User> usersPCM = userClaimsPCM.stream().map(UserClaims::getUser).collect(Collectors.toList());
        return usersPCM;
    }

    public List<User> getConferenceSCM(Conference conference)
    {
        List<User> allSCM = new ArrayList<>();

        List<User> usersSCM = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.SCM);
        List<User> usersCSCM = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.CHAIR_SCM);
        List<User> usersCCSCM = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.CO_CHAIR_SCM);

        allSCM.addAll(usersSCM);
        allSCM.addAll(usersCSCM);
        allSCM.addAll(usersCCSCM);

        return allSCM;
    }

    public List<User> getConferencePCM(Conference conference)
    {
        List<User> allPCM = new ArrayList<>();

        List<User> usersPCM = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.PCM);
        List<User> usersCPCM = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.CHAIR_PCM);
        List<User> usersCCPCM = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.CO_CHAIR_PCM);

        allPCM.addAll(usersPCM);
        allPCM.addAll(usersCPCM);
        allPCM.addAll(usersCCPCM);

        return allPCM;
    }
    // START OF CLAIMS SECTION

    public void addRoleToUser(User user, Conference conference, ENUMERATION_ROLES role)
    {
        Optional<UserClaims> claim = this.roleRepository.findByConferenceAndUserAndRole(conference, user, role);
        if(claim.isPresent())
        {
            throw new ServiceException("[ERROR] User has been assigned with the specified role already.");
        }
        UserClaims theNewRole = new UserClaims(user, conference, role);
        this.roleRepository.save(theNewRole);
    }

    public void removeRoleFromUser(User user, Conference conference, ENUMERATION_ROLES role)
    {
        Optional<UserClaims> claim = this.roleRepository.findByConferenceAndUserAndRole(conference, user, role);
        if(claim.isEmpty())
        {
            throw new ServiceException("[ERROR] User doesn't have the specified role.");
        }
        this.roleRepository.delete(claim.get());
    }

    public void addAdmin(String userEmail)
    {
        this.adminRepository.save(this.getUserAfterValidation(userEmail));
    }

    public void removeAdmin(String userEmail)
    {
        this.adminRepository.delete(this.getUserAfterValidation(userEmail));
    }

    public void addSCM(String userEmail, Conference conference)
    {
        this.addRoleToUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.SCM);
    }

    public void removeSCM(String userEmail, Conference conference)
    {
        this.removeRoleFromUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.SCM);
    }

    public void addCSCM(String userEmail, Conference conference)
    {
        this.addRoleToUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.CHAIR_SCM);
    }

    public void removeCSCM(String userEmail, Conference conference)
    {
        this.removeRoleFromUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.CHAIR_SCM);
    }

    public void addCCSCM(String userEmail, Conference conference)
    {
        this.addRoleToUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.CO_CHAIR_SCM);
    }

    public void removeCCSCM(String userEmail, Conference conference)
    {
        this.removeRoleFromUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.CO_CHAIR_SCM);
    }

    public void addPCM(String userEmail, Conference conference)
    {
        this.addRoleToUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.PCM);
    }

    public void removePCM(String userEmail, Conference conference)
    {
        this.removeRoleFromUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.PCM);
    }

    public void addCPCM(String userEmail, Conference conference)
    {
        this.addRoleToUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.CHAIR_PCM);
    }

    public void removeCPCM(String userEmail, Conference conference)
    {
        this.removeRoleFromUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.CHAIR_PCM);
    }

    public void addCCPCM(String userEmail, Conference conference)
    {
        this.addRoleToUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.CO_CHAIR_PCM);
    }

    public void removeCCPCM(String userEmail, Conference conference)
    {
        this.removeRoleFromUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.CO_CHAIR_PCM);
    }

    public void addSectionChair(String userEmail, Conference conference)
    {
        this.addRoleToUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.SESSION_CHAIR);
    }

    public void removeSectionChair(String userEmail, Conference conference)
    {
        this.removeRoleFromUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.SESSION_CHAIR);
    }
    // END OF CLAIMS SECTION


    public List<UserClaims> getAllRolesForUser(User user)
    {
        if(null == user)
        {
            throw new ServiceException("[ERROR] Null user given for roles search");
        }
        List<UserClaims> claimsList = this.roleRepository.findAll().stream().filter( x-> x.getUser().getEmail().equals(user.getEmail())).collect(Collectors.toList());
        return claimsList;
    }

    public List<User> getUsersInEmailList(List<String> emails)
    {
        List<User> users = this.repository.findAll();
        return users.stream().filter(x -> emails.stream().anyMatch(k -> k.equals(x.getEmail()))).collect(Collectors.toList());
    }
}
