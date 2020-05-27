package theotherhalf.superconference.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.Section;
import theotherhalf.superconference.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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

    @JsonProperty
    private List<JsonEmailDTO> participants;
    private List<JsonEmailDTO> scms;
    private JsonEmailDTO cscm;
    private JsonEmailDTO ccscm;
    private List<JsonEmailDTO> pcms;
    private JsonEmailDTO cpcm;
    private JsonEmailDTO ccpcm;
    private List<SectionDTO> sections;

    public ConferenceDTO(Long id,
                         @NotBlank String name,
                         @NotBlank String description,
                         @NotNull Date zeroDeadline,
                         Date abstractDeadline,
                         Date proposalDeadline,
                         Date biddingDeadline,
                         Date evaluationDeadline,
                         Date presentationDeadline,
                         List<JsonEmailDTO> participants,
                         List<JsonEmailDTO> scms,
                         JsonEmailDTO cscm,
                         JsonEmailDTO ccscm,
                         List<JsonEmailDTO> pcms,
                         JsonEmailDTO cpcm,
                         JsonEmailDTO ccpcm,
                         List<SectionDTO> sections)
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
        this.participants = participants;
        this.scms = scms;
        this.cscm = cscm;
        this.ccscm = ccscm;
        this.pcms = pcms;
        this.cpcm = cpcm;
        this.ccpcm = ccpcm;
        this.sections = sections;
    }

    public ConferenceDTO()
    {

    }


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

    public List<SectionDTO> getSections() {
        return sections;
    }

    public void setSections(List<SectionDTO> sections) {
        this.sections = sections;
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

    public List<JsonEmailDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<JsonEmailDTO> participants) {
        this.participants = participants;
    }

    public List<JsonEmailDTO> getScms() {
        return scms;
    }

    public void setScms(List<JsonEmailDTO> scms) {
        this.scms = scms;
    }

    public JsonEmailDTO getCscm() {
        return cscm;
    }

    public void setCscm(JsonEmailDTO cscm) {
        this.cscm = cscm;
    }

    public JsonEmailDTO getCcscm() {
        return ccscm;
    }

    public void setCcscm(JsonEmailDTO ccscm) {
        this.ccscm = ccscm;
    }

    public List<JsonEmailDTO> getPcms() {
        return pcms;
    }

    public void setPcms(List<JsonEmailDTO> pcms) {
        this.pcms = pcms;
    }

    public JsonEmailDTO getCpcm() {
        return cpcm;
    }

    public void setCpcm(JsonEmailDTO cpcm) {
        this.cpcm = cpcm;
    }

    public JsonEmailDTO getCcpcm() {
        return ccpcm;
    }

    public void setCcpcm(JsonEmailDTO ccpcm) {
        this.ccpcm = ccpcm;
    }
}
