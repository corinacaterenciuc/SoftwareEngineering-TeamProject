package theotherhalf.superconference.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import theotherhalf.superconference.domain.Proposal;
import theotherhalf.superconference.domain.Section;
import theotherhalf.superconference.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionDTO
{
    @JsonProperty
    private Long id;

    @JsonProperty
    private String chair;

    @JsonProperty
    private List<String> topics;

    @JsonProperty
    private List<ProposalDTO> proposals;

    @JsonProperty
    private List<JsonEmailDTO> participants;

    @JsonProperty
    private Integer room;

    public SectionDTO(String chair, List<String> topics, List<ProposalDTO> proposals, List<JsonEmailDTO> participants, Integer room)
    {
        this.chair = chair;
        this.topics = topics;
        this.proposals = proposals;
        this.participants = participants;
        this.room = room;
    }

    public SectionDTO()
    {
    }

    public static SectionDTO toDTO(Long confId, Section section)
    {
        return new SectionDTO(section.getChair().getEmail(),
                              section.getTopic(),
                              section.getProposals().stream().map(x -> ProposalDTO.toDTO(confId, x)).collect(Collectors.toList()),
                              section.getParticipants().stream().map(x -> new JsonEmailDTO(x.getEmail())).collect(Collectors.toList()),
                              section.getRoom());
    }

    public String getChair() {
        return chair;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<ProposalDTO> getProposals() {
        return proposals;
    }

    public void setProposals(List<ProposalDTO> proposals) {
        this.proposals = proposals;
    }

    public List<JsonEmailDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<JsonEmailDTO> participants) {
        this.participants = participants;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }
}
