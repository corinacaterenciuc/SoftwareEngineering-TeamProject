package theotherhalf.superconference.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import theotherhalf.superconference.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDTO
{
    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    @NotBlank
    private String email;

    @NotNull
    private Long password;


    public UserDTO(@JsonProperty("firstname") String first_name,
                   @JsonProperty("lastname") String last_name,
                   @JsonProperty("email") String email,
                   @JsonProperty("pass") Long password)
    {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
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
        return new User(dto.getFirst_name(),
                        dto.getLast_name(),
                        dto.getEmail(),
                        dto.getPassword());
    }

    public static UserDTO toDTO(final User user)
    {
        return new UserDTO(user.getFirst_name(),
                           user.getLast_name(),
                           user.getEmail(),
                           user.getPassword());
    }
}
