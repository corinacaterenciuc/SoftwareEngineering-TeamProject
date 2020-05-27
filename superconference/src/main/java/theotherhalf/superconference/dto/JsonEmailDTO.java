package theotherhalf.superconference.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonEmailDTO
{
    private String email;

    public JsonEmailDTO()
    {

    }

    public JsonEmailDTO(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return this.email;
    }
}
