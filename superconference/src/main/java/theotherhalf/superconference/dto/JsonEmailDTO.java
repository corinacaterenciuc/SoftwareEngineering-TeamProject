package theotherhalf.superconference.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonEmailDTO
{
    private String email;

    JsonEmailDTO()
    {

    }

    JsonEmailDTO(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return this.email;
    }
}
