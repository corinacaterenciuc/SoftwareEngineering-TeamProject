package theotherhalf.superconference.api.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class JwtLoginResponse implements Serializable
{

    private static final long serialVersionUID = -8091879091924046844L;

    private final String token;

    private String firstname;
    private String lastname;


    public JwtLoginResponse(String jwttoken)
    {
        this.token = jwttoken;
    }

    public JwtLoginResponse(String jwttoken, String lastName, String firstName)
    {
        this.token = jwttoken;
        this.firstname = firstName;
        this.lastname = lastName;
    }

    public String getToken()
    {
        return this.token;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}