package theotherhalf.superconference.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConferenceDTO
{
    @JsonProperty
    private Long id;

    @NotBlank
    @JsonProperty
    private String name;

    @NotBlank
    @JsonProperty
    private String description;

    @NotNull
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date zeroDeadline;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date abstractDeadline;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date proposalDeadline;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date biddingDeadline;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date evaluationDeadline;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date presentationDeadline;


    public ConferenceDTO(Long id,
                         @NotBlank String name,
                         @NotBlank String description,
                         @NotNull Date zeroDeadline,
                         Date abstractDeadline,
                         Date proposalDeadline,
                         Date biddingDeadline,
                         Date evaluationDeadline,
                         Date presentationDeadline)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.zeroDeadline = zeroDeadline;
        this.abstractDeadline = abstractDeadline;
        this.proposalDeadline = proposalDeadline;
        this.biddingDeadline = biddingDeadline;
        this.evaluationDeadline = evaluationDeadline;
        this.presentationDeadline = presentationDeadline;
    }

    public ConferenceDTO(
            @JsonProperty @NotBlank String name,
            @JsonProperty @NotBlank String description,
            @JsonProperty @NotNull Date zeroDeadline,
            @JsonProperty  Date abstractDeadline,
            @JsonProperty  Date proposalDeadline,
            @JsonProperty  Date biddingDeadline,
            @JsonProperty  Date evaluationDeadline,
            @JsonProperty  Date presentationDeadline) {
        this.name = name;
        this.description = description;
        this.zeroDeadline = zeroDeadline;
        this.abstractDeadline = abstractDeadline;
        this.proposalDeadline = proposalDeadline;
        this.biddingDeadline = biddingDeadline;
        this.evaluationDeadline = evaluationDeadline;
        this.presentationDeadline = presentationDeadline;
    }

    public ConferenceDTO()
    {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getEvaluationDeadline() {
        return evaluationDeadline;
    }

    public void setEvaluationDeadline(Date evaluationDeadline) {
        this.evaluationDeadline = evaluationDeadline;
    }

    public static Conference toDomain(ConferenceDTO dto)
    {
        return new Conference(dto.getName(),
                              dto.getDescription(),
                              dto.getZeroDeadline(),
                              dto.getAbstractDeadline(),
                              dto.getProposalDeadline(),
                              dto.getBiddingDeadline(),
                              dto.getEvaluationDeadline(),
                              dto.getPresentationDeadline());
    }

    public static ConferenceDTO toDTO(final Conference conference)
    {
        return new ConferenceDTO(conference.getID(),
                                 conference.getName(),
                                 conference.getDescription(),
                                 conference.getZeroDeadline(),
                                 conference.getAbstractDeadline(),
                                 conference.getProposalDeadline(),
                                 conference.getBiddingDeadline(),
                                 conference.getEvaluationDeadline(),
                                 conference.getPresentationDeadline());
    }
}
