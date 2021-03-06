package theotherhalf.superconference.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.ENUMERATION_ROLES;
import theotherhalf.superconference.domain.CMSUser;
import theotherhalf.superconference.domain.UserClaims;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserClaimsDTO
{
    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private ConferenceDTO conference;

    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private UserDTO user;

    @NotNull
    private ENUMERATION_ROLES role;

    public UserClaimsDTO()
    {

    }

    public UserClaimsDTO(ConferenceDTO conference, UserDTO user, ENUMERATION_ROLES role)
    {
        this.conference = conference;
        this.user = user;
        this.role = role;
    }

    public ConferenceDTO getConference()
    {
        return conference;
    }

    public void setConference(ConferenceDTO conference)
    {
        this.conference = conference;
    }

    public UserDTO getUser()
    {
        return user;
    }

    public void setUser(UserDTO user)
    {
        this.user = user;
    }

    public ENUMERATION_ROLES getRole()
    {
        return role;
    }

    public void setRole(ENUMERATION_ROLES role)
    {
        this.role = role;
    }

    public static UserClaimsDTO toDTO(UserClaims userClaims)
    {
        ConferenceDTO conferenceDTO = ConferenceDTO.toDTO(userClaims.getConference());
        UserDTO userDTO = UserDTO.toDTO(userClaims.getUser());
        return new UserClaimsDTO(conferenceDTO, userDTO, userClaims.getRole());
    }

    public static UserClaims toDomain(UserClaimsDTO userClaimsDTO)
    {
        Conference conference = ConferenceDTO.toDomain(userClaimsDTO.getConference());
        CMSUser user = UserDTO.toDomain(userClaimsDTO.getUser());
        return new UserClaims(user, conference, userClaimsDTO.getRole());
    }
}
