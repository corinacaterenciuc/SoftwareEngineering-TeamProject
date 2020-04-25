package theotherhalf.superconference.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name="cmsuserclaims")
public class UserClaims extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    private Conference conference;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @NotNull
    private ENUMERATION_ROLES role;

    public UserClaims(@JsonProperty("user") User user,
                      @JsonProperty("conference") Conference conference,
                      @JsonProperty("role") ENUMERATION_ROLES role)
    {
        this.user = user;
        this.role = role;
        this.conference = conference;
    }

    public UserClaims(User user, Conference conference)
    {
        this.conference = conference;
        this.user = user;
        this.role = ENUMERATION_ROLES.USER;
    }

    public UserClaims()
    {

    }

    public Conference getConference()
    {
        return conference;
    }

    public void setConference(Conference conference)
    {
        this.conference = conference;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
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

}
