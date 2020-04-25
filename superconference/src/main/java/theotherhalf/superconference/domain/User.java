package theotherhalf.superconference.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="CMSUser")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity
{
    @Column(nullable = false, name="first_name")
    private String first_name;

    @Column(nullable = false, name="last_name")
    private String last_name;

    @Column(nullable = false, unique = true, name="email")
    private String email;

    @Column(nullable = false, name="_password")
    private Long password;

    public User()
    {

    }

    public User(Long ID,
                String first_name,
                String last_name,
                String email,
                Long password)
    {
        this.setID(ID);
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public User(String first_name,
                String last_name,
                String email,
                Long password)
    {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "User{" + "first_name='" + first_name + '\'' + ", last_name='" + last_name + '\'' + ", email='" + email + "\'}";
    }


    public String getFirst_name()
    {
        return first_name;
    }

    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }

    public String getLast_name()
    {
        return last_name;
    }

    public void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Long getPassword()
    {
        return password;
    }

    public void setPassword(Long password)
    {
        this.password = password;
    }

}
