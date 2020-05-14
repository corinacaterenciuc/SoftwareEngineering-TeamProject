package theotherhalf.superconference.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="conference")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Conference extends BaseEntity
{
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Date zeroDeadline;

    @NotNull
    private Date abstractDeadline;

    public Conference(@NotBlank String name,
                      @NotBlank String description,
                      @NotNull Date zeroDeadline,
                      @NotNull Date abstractDeadline,
                      @NotNull Date proposalDeadline,
                      @NotNull Date biddingDeadline,
                      @NotNull Date presentationDeadline) {
        this.name = name;
        this.description = description;
        this.zeroDeadline = zeroDeadline;
        this.abstractDeadline = abstractDeadline;
        this.proposalDeadline = proposalDeadline;
        this.biddingDeadline = biddingDeadline;
        this.presentationDeadline = presentationDeadline;
    }

    @NotNull
    private Date proposalDeadline;

    @NotNull
    private Date biddingDeadline;

    @NotNull
    private Date presentationDeadline;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> sections;

    public Conference() {
    }


    public Conference(
            @NotBlank String name,
            @NotBlank String description,
            @NotNull Date zeroDeadline,
            @NotNull Date abstractDeadline,
            @NotNull Date proposalDeadline,
            @NotNull Date biddingDeadline,
            @NotNull Date presentationDeadline,
            List<Section> sections) {
        this.name = name;
        this.description = description;
        this.zeroDeadline = zeroDeadline;
        this.abstractDeadline = abstractDeadline;
        this.proposalDeadline = proposalDeadline;
        this.biddingDeadline = biddingDeadline;
        this.presentationDeadline = presentationDeadline;
        this.sections = sections;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getZeroDeadline() {
        return zeroDeadline;
    }

    public void setZeroDeadline(Date zeroDeadline) {
        this.zeroDeadline = zeroDeadline;
    }

    public Date getAbstractDeadline() {
        return abstractDeadline;
    }

    public void setAbstractDeadline(Date abstractDeadline) {
        this.abstractDeadline = abstractDeadline;
    }

    public Date getProposalDeadline() {
        return proposalDeadline;
    }

    public void setProposalDeadline(Date proposalDeadline) {
        this.proposalDeadline = proposalDeadline;
    }

    public Date getBiddingDeadline() {
        return biddingDeadline;
    }

    public void setBiddingDeadline(Date biddingDeadline) {
        this.biddingDeadline = biddingDeadline;
    }

    public Date getPresentationDeadline() {
        return presentationDeadline;
    }

    public void setPresentationDeadline(Date presentationDeadline) {
        this.presentationDeadline = presentationDeadline;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

}