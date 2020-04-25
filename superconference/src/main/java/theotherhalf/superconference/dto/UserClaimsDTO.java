package theotherhalf.superconference.dto;

import com.sun.istack.NotNull;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.ENUMERATION_ROLES;
import theotherhalf.superconference.domain.User;
import theotherhalf.superconference.domain.UserClaims;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

public class UserClaimsDTO
{
    @ManyToOne(fetch = FetchType.LAZY)
    private ConferenceDTO conference;

    @ManyToOne(fetch = FetchType.LAZY)
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
        User user = UserDTO.toDomain(userClaimsDTO.getUser());
        return new UserClaims(user, conference, userClaimsDTO.getRole());
    }
}
