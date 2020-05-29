package theotherhalf.superconference.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theotherhalf.superconference.domain.*;
import theotherhalf.superconference.exceptions.CMSForbidden;
import theotherhalf.superconference.exceptions.ConferenceManagementSystemException;
import theotherhalf.superconference.exceptions.ServiceException;
import theotherhalf.superconference.exceptions.ValidationException;
import theotherhalf.superconference.repository.AdminRepository;
import theotherhalf.superconference.repository.RoleRepository;
import theotherhalf.superconference.repository.UserRepository;
import theotherhalf.superconference.validators.UserValidator;
import java.security.MessageDigest;



import javax.sql.rowset.serial.SerialException;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
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

    @Autowired
    private ConferenceService conferenceService;


    private String hashPassword(String password) throws NoSuchAlgorithmException
    {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        String encryptedString = new String(messageDigest.digest());
        return encryptedString;
    }
    public CMSUser addUser(CMSUser user) throws ConferenceManagementSystemException
    {
        if (this.repository.findById(user.getEmail()).isPresent())
        {
            throw new ServiceException("[ERROR] Email address already in use");
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

    public boolean isAdmin(String email)
    {
        return this.adminRepository.findAll().stream().anyMatch(x -> x.getEmail().equals(email));
    }

    public boolean isPCM(String email, Long confId)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        return this.roleRepository.findByConferenceAndUserAndRole(conference, this.getUserAfterValidation(email), ENUMERATION_ROLES.PCM).isPresent();
    }

    public boolean isAnyPCM(String email, Long confId)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        return
                this.roleRepository.findByConferenceAndUserAndRole(conference, this.getUserAfterValidation(email), ENUMERATION_ROLES.PCM).isPresent()
                || this.roleRepository.findByConferenceAndUserAndRole(conference, this.getUserAfterValidation(email), ENUMERATION_ROLES.CHAIR_PCM).isPresent()
                || this.roleRepository.findByConferenceAndUserAndRole(conference, this.getUserAfterValidation(email), ENUMERATION_ROLES.CO_CHAIR_PCM).isPresent();
    }

    public boolean isSCM(String email, Long confId)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        return this.roleRepository.findByConferenceAndUserAndRole(conference, this.getUserAfterValidation(email), ENUMERATION_ROLES.SCM).isPresent();
    }

    public boolean isAnySCM(String email, Long confId)
    {
        Conference conference = this.conferenceService.getConferenceAfterValidation(confId);
        return
                this.roleRepository.findByConferenceAndUserAndRole(conference, this.getUserAfterValidation(email), ENUMERATION_ROLES.SCM).isPresent()
                        || this.roleRepository.findByConferenceAndUserAndRole(conference, this.getUserAfterValidation(email), ENUMERATION_ROLES.CHAIR_SCM).isPresent()
                        || this.roleRepository.findByConferenceAndUserAndRole(conference, this.getUserAfterValidation(email), ENUMERATION_ROLES.CO_CHAIR_SCM).isPresent();
    }

    @Transactional
    public CMSUser updateUser(CMSUser sameUser)
    {
        if(null == sameUser)
        {
            throw new ServiceException("[ERROR] Null CMSUser given to update");
        }
        Optional<CMSUser> tempUser = this.repository.findById(sameUser.getEmail());
        if(tempUser.isEmpty())
        {
            throw new ServiceException("[ERROR] CMSUser doesn't exist.");
        }
        CMSUser repoUser = tempUser.get();
        CMSUser dummyUser = new CMSUser();
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

    public List<CMSUser> getAllUsers()
    {
        return this.repository.findAll();
    }

    public Optional<CMSUser> findByEmail(String email)
    {
        return this.repository.findById(email);
    }

    public CMSUser getUserAfterValidation(String email)
    {
        if(null == email)
        {
            throw new ServiceException("[ERROR] Null email address given");
        }
        Optional<CMSUser> opUser = this.repository.findById(email);
        if(opUser.isEmpty())
        {
            throw new ServiceException("[ERROR] No such user exists.");
        }
        return opUser.get();
    }

    public boolean hasRole(CMSUser user, Conference conference, ENUMERATION_ROLES role)
    {
        return this.roleRepository.findByConferenceAndUserAndRole(conference, user, role).isPresent();
    }

    public List<CMSUser> getConferenceUsersByRole(Conference conference, ENUMERATION_ROLES role)
    {
        List<UserClaims> userClaimsPCM = this.roleRepository.findByConferenceAndRole(conference, role);
        List<CMSUser> usersPCM = userClaimsPCM.stream().map(UserClaims::getUser).collect(Collectors.toList());
        return usersPCM;
    }

    public List<UserClaims> getAllClaimsFromConference(Conference conference)
    {
        return this.roleRepository.findByConference(conference);
    }

    @Transactional
    public void deleteAllClaimsFromConference(Conference conference)
    {
        List<UserClaims> claims = this.getAllClaimsFromConference(conference);
        claims.forEach(x -> this.roleRepository.delete(x));
    }

    public List<CMSUser> getConferenceClaimsByRole(Conference conference, ENUMERATION_ROLES role)
    {
        List<CMSUser> users = this.getConferenceUsersByRole(conference, role);
        users = users.stream().distinct().collect(Collectors.toList());
        return users;
    }

    public List<CMSUser> getConferenceSCM(Conference conference)
    {
        List<CMSUser> allSCM = new ArrayList<>();

        List<CMSUser> usersSCM = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.SCM);
        List<CMSUser> usersCSCM = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.CHAIR_SCM);
        List<CMSUser> usersCCSCM = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.CO_CHAIR_SCM);

        allSCM.addAll(usersSCM);
        allSCM.addAll(usersCSCM);
        allSCM.addAll(usersCCSCM);

        allSCM = allSCM.stream().distinct().collect(Collectors.toList());
        return allSCM;
    }

    public List<CMSUser> getConferencePCM(Conference conference)
    {
        List<CMSUser> allPCM = new ArrayList<>();

        List<CMSUser> usersPCM = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.PCM);
        List<CMSUser> usersCPCM = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.CHAIR_PCM);
        List<CMSUser> usersCCPCM = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.CO_CHAIR_PCM);

        allPCM.addAll(usersPCM);
        allPCM.addAll(usersCPCM);
        allPCM.addAll(usersCCPCM);

        allPCM = allPCM.stream().distinct().collect(Collectors.toList());
        return allPCM;
    }

    public CMSUser getConferenceCPCM(Conference conference)
    {
        List<CMSUser> users = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.CHAIR_PCM);
        if(users.size() == 0)
        {
            return null;
        }
        else
        {
            return users.get(0);
        }
    }
    public CMSUser getConferenceCCPCM(Conference conference)
    {
        List<CMSUser> users = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.CO_CHAIR_PCM);
        if(users.size() == 0)
        {
            return null;
        }
        else
        {
            return users.get(0);
        }
    }
    public CMSUser getConferenceCSCM(Conference conference)
    {
        List<CMSUser> users = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.CHAIR_SCM);
        if(users.size() == 0)
        {
            return null;
        }
        else
        {
            return users.get(0);
        }
    }
    public CMSUser getConferenceCCSCM(Conference conference)
    {
        List<CMSUser> users = this.getConferenceUsersByRole(conference, ENUMERATION_ROLES.CO_CHAIR_SCM);
        if(users.size() == 0)
        {
            return null;
        }
        else
        {
            return users.get(0);
        }
    }
    // START OF CLAIMS SECTION

    public void addRoleToUser(CMSUser user, Conference conference, ENUMERATION_ROLES role)
    {
        Optional<UserClaims> claim = this.roleRepository.findByConferenceAndUserAndRole(conference, user, role);
        if(claim.isEmpty())
        {
            UserClaims theNewRole = new UserClaims(user, conference, role);
            this.roleRepository.save(theNewRole);
        }

    }

    @Transactional
    public void removeRoleFromUser(CMSUser user, Conference conference, ENUMERATION_ROLES role)
    {
        Optional<UserClaims> claim = this.roleRepository.findByConferenceAndUserAndRole(conference, user, role);
        if(!claim.isEmpty())
        {
            this.roleRepository.delete(claim.get());
        }
    }

    public void addAdmin(String userEmail)
    {
        this.adminRepository.save(new AdminUser(userEmail, true));
    }

    public void removeAdmin(String userEmail)
    {
        this.adminRepository.delete(this.adminRepository.findAdminUserByEmail(userEmail));
    }

    public void addSCM(String userEmail, Conference conference)
    {
        this.addRoleToUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.SCM);
    }

    public void removeSCM(String userEmail, Conference conference)
    {
        this.removeRoleFromUser(this.getUserAfterValidation(userEmail), conference, ENUMERATION_ROLES.SCM);
    }

    @Transactional
    public void removeCPCM(Conference conference)
    {
        if(0 != this.roleRepository.findByConferenceAndRole(conference, ENUMERATION_ROLES.CHAIR_PCM).size())
        {
            UserClaims claim = this.roleRepository.findByConferenceAndRole(conference, ENUMERATION_ROLES.CHAIR_PCM).get(0);
            this.removeRoleFromUser(claim.getUser(), conference, ENUMERATION_ROLES.CHAIR_PCM);
        }
    }

    @Transactional
    public void removeCCPCM(Conference conference)
    {
        if(0 != this.roleRepository.findByConferenceAndRole(conference, ENUMERATION_ROLES.CO_CHAIR_PCM).size())
        {
            UserClaims claim = this.roleRepository.findByConferenceAndRole(conference, ENUMERATION_ROLES.CO_CHAIR_PCM).get(0);
            this.removeRoleFromUser(claim.getUser(), conference, ENUMERATION_ROLES.CO_CHAIR_PCM);
        }
    }

    @Transactional
    public void removeCSCM(Conference conference)
    {
        if(0 != this.roleRepository.findByConferenceAndRole(conference, ENUMERATION_ROLES.CHAIR_SCM).size())
        {
            UserClaims claim = this.roleRepository.findByConferenceAndRole(conference, ENUMERATION_ROLES.CHAIR_SCM).get(0);
            this.removeRoleFromUser(claim.getUser(), conference, ENUMERATION_ROLES.CHAIR_SCM);
        }
    }

    @Transactional
    public void removeCCSCM(Conference conference)
    {
        if(0 != this.roleRepository.findByConferenceAndRole(conference, ENUMERATION_ROLES.CO_CHAIR_SCM).size())
        {
            UserClaims claim = this.roleRepository.findByConferenceAndRole(conference, ENUMERATION_ROLES.CO_CHAIR_SCM).get(0);
            this.removeRoleFromUser(claim.getUser(), conference, ENUMERATION_ROLES.CO_CHAIR_SCM);
        }
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

    @Transactional
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


    public List<UserClaims> getAllRolesForUser(CMSUser user)
    {
        if(null == user)
        {
            throw new ServiceException("[ERROR] Null user given for roles search");
        }
        List<UserClaims> claimsList = this.roleRepository.findAll().stream().filter( x-> x.getUser().getEmail().equals(user.getEmail())).collect(Collectors.toList());
        return claimsList;
    }

    public List<CMSUser> getUsersInEmailList(List<String> emails)
    {
        List<CMSUser> users = this.repository.findAll();
        return users.stream().filter(x -> emails.stream().anyMatch(k -> k.equals(x.getEmail()))).collect(Collectors.toList());
    }
}
