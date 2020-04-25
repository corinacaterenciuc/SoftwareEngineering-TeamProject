package theotherhalf.superconference.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ConferenceDTO
{
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private Date firstDeadline;
    private Date secondDeadline;
    private Date thirdDeadline;


    public ConferenceDTO(@JsonProperty("title") String title,
                   @JsonProperty("description") String description,
                   @JsonProperty("firstDL") Date firstDeadline,
                   @JsonProperty("secondDL") Date secondDeadline,
                   @JsonProperty("thirdDL") Date thirdDeadline)
    {
        this.title = title;
        this.description = description;
        this.firstDeadline = firstDeadline;
        this.secondDeadline = secondDeadline;
        this.thirdDeadline = thirdDeadline;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getFirstDeadline()
    {
        return firstDeadline;
    }

    public void setFirstDeadline(Date firstDeadline)
    {
        this.firstDeadline = firstDeadline;
    }

    public Date getSecondDeadline()
    {
        return secondDeadline;
    }

    public void setSecondDeadline(Date secondDeadline)
    {
        this.secondDeadline = secondDeadline;
    }

    public Date getThirdDeadline()
    {
        return thirdDeadline;
    }

    public void setThirdDeadline(Date thirdDeadline)
    {
        this.thirdDeadline = thirdDeadline;
    }

    public static Conference toDomain(ConferenceDTO dto)
    {
        return new Conference(dto.getTitle(),
                dto.getDescription(),
                dto.getFirstDeadline(),
                dto.getSecondDeadline(),
                dto.getThirdDeadline());
    }

    public static ConferenceDTO toDTO(final Conference conference)
    {
        return new ConferenceDTO(conference.getTitle(),
                conference.getDescription(),
                conference.getFirstDeadline(),
                conference.getSecondDeadline(),
                conference.getThirdDeadline());
    }
}
