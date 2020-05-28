package theotherhalf.superconference.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import theotherhalf.superconference.domain.CMSUser;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO
{
    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("email")
    @NotBlank
    private String email;

    @JsonProperty("password")
    private String password;


    public UserDTO( String firstName,
                    String lastName,
                    String email,
                    String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

//    public UserDTO(String firstName,
//                   String lastName,
//                   String email)
//    {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//    }


    public String getfirstName()
    {
        return firstName;
    }

    public void setfirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getlastName()
    {
        return lastName;
    }

    public void setlastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public static CMSUser toDomain(UserDTO dto)
    {
        return new CMSUser(dto.getfirstName(),
                        dto.getlastName(),
                        dto.getEmail());
    }

    public static UserDTO toDTO(final CMSUser user)
    {
        return new UserDTO(user.getFirstName(),
                           user.getLastName(),
                           user.getEmail(),
                           user.getPassword());
    }
}
