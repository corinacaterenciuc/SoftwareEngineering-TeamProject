package theotherhalf.superconference.domain;

import org.springframework.data.relational.core.mapping.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table
public class UserClaims extends BaseEntity
{
    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Conference conference;

    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private CMSUser user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ENUMERATION_ROLES role;

    public UserClaims(@JsonProperty("user") CMSUser user,
                      @JsonProperty("conference") Conference conference,
                      @JsonProperty("role") ENUMERATION_ROLES role)
    {
        this.user = user;
        this.role = role;
        this.conference = conference;
    }

    public UserClaims(CMSUser user, Conference conference)
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

    public CMSUser getUser()
    {
        return user;
    }

    public void setUser(CMSUser user)
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
