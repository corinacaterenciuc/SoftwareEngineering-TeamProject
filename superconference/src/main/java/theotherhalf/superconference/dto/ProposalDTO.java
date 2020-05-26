package theotherhalf.superconference.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProposalDTO
{
    @JsonProperty
    private Long id;

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
