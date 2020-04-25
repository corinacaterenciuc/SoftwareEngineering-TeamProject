package theotherhalf.superconference.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name="conference")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Conference extends BaseEntity
{
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private Date firstDeadline;
    private Date secondDeadline;
    private Date thirdDeadline;


    public Conference()
    {

    }

    public Conference(String title,
                      String description,
                      Date firstDeadline,
                      Date secondDeadline,
                      Date thirdDeadline
    )
    {
        this.title = title;
        this.description = description;
        this.firstDeadline = firstDeadline;
        this.secondDeadline = secondDeadline;
        this.thirdDeadline = thirdDeadline;
    }

    @Override
    public String toString()
    {
        return "Conference{" + "title='" + title + '\'' + ", description='" + description + '\'' + ", firstDeadline=" + firstDeadline + ", secondDeadline=" + secondDeadline + ", thirdDeadline=" + thirdDeadline + '}';
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


}
