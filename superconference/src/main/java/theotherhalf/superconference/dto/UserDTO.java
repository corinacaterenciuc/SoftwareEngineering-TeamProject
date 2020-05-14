package theotherhalf.superconference.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import theotherhalf.superconference.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDTO
{
    private String firstName;

    private String lastName;

    @NotBlank
    private String email;

    @NotNull
    private Long password;


    public UserDTO(@JsonProperty("firstname") String firstName,
                   @JsonProperty("lastname") String lastName,
                   @JsonProperty("email") String email,
                   @JsonProperty("pass") Long password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


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
    public Long getPassword()
    {
        return password;
    }

    public void setPassword(Long password)
    {
        this.password = password;
    }

    public static User toDomain(UserDTO dto)
    {
        return new User(dto.getfirstName(),
                        dto.getlastName(),
                        dto.getEmail(),
                        dto.getPassword());
    }

    public static UserDTO toDTO(final User user)
    {
        return new UserDTO(user.getFirstName(),
                           user.getLastName(),
                           user.getEmail(),
                           user.getPassword());
    }
}
