package theotherhalf.superconference.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProposalDTO
{
    private String email;
    private String title;

    public ProposalDTO()
    {
    }

    public ProposalDTO(String email, String title)
    {

    }
    public String getEmail()
    {
        return this.email;
    }
    public String getTitle()
    {
        return this.title;
    }
}
