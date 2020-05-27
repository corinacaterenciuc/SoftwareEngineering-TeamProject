package theotherhalf.superconference.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.beanutils.converters.StringArrayConverter;
import theotherhalf.superconference.domain.BaseEntity;
import theotherhalf.superconference.domain.Proposal;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProposalDTO
{
    @JsonProperty
    private Long id;

    private Long conference;
    private String proposalName;
    private JsonEmailDTO author;
    private String filePath;
    @JsonProperty("abstract")
    private String abstrac;
    private List<String> topics;
    private List<JsonEmailDTO> coAuthors;
    private List<String> keywords;
    private List<JsonEmailDTO> bidders;
    //private List<Long> reviews;
    private List<JsonEmailDTO> reviewers;

    public ProposalDTO(Long id,
                       Long conference,
                       String proposalName,
                       JsonEmailDTO author,
                       String filePath,
                       String abstrac,
                       List<String> topics,
                       List<JsonEmailDTO> coAuthors,
                       List<String> keywords,
                       List<JsonEmailDTO> bidders,
                       List<JsonEmailDTO> reviewers)
    {
        this.id = id;
        this.conference = conference;
        this.proposalName = proposalName;
        this.author = author;
        this.filePath = filePath;
        this.abstrac = abstrac;
        this.topics = topics;
        this.coAuthors = coAuthors;
        this.keywords = keywords;
        this.bidders = bidders;
        this.reviewers = reviewers;
    }

    public ProposalDTO() {
    }

    public static ProposalDTO toDTO(Long confId, Proposal proposal)
    {
        return new ProposalDTO(
                proposal.getID(),
                confId,
                proposal.getProposalName(),
                new JsonEmailDTO(proposal.getAuthor().getEmail()),
                proposal.getFilePath(),
                proposal.getAbstractDescription(),
                proposal.getTopics(),
                proposal.getCoAuthors().stream().map(x -> new JsonEmailDTO(x.getEmail())).collect(Collectors.toList()),
                proposal.getKeywords(),
                proposal.getBiddingPeople().stream().map(x-> new JsonEmailDTO(x.getEmail())).collect(Collectors.toList()),
                proposal.getReviewers().stream().map(x -> new JsonEmailDTO(x.getEmail())).collect(Collectors.toList()));
    }

    public static Proposal toPartialDomain(ProposalDTO proposalDTO)
    {
        return new Proposal(
                proposalDTO.getProposalName(),
                proposalDTO.getFilePath(),
                proposalDTO.getAbstrac(),
                proposalDTO.getTopics(),
                proposalDTO.getKeywords()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConference() {
        return conference;
    }

    public void setConference(Long conference) {
        this.conference = conference;
    }

    public String getProposalName() {
        return proposalName;
    }

    public void setProposalName(String proposalName) {
        this.proposalName = proposalName;
    }

    public JsonEmailDTO getAuthor() {
        return author;
    }

    public void setAuthor(JsonEmailDTO author) {
        this.author = author;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAbstrac() {
        return abstrac;
    }

    public void setAbstrac(String abstrac) {
        this.abstrac = abstrac;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<JsonEmailDTO> getCoAuthors() {
        return coAuthors;
    }

    public void setCoAuthors(List<JsonEmailDTO> coAuthors) {
        this.coAuthors = coAuthors;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<JsonEmailDTO> getBidders() {
        return bidders;
    }

    public void setBidders(List<JsonEmailDTO> bidders) {
        this.bidders = bidders;
    }

    public List<JsonEmailDTO> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<JsonEmailDTO> reviewers) {
        this.reviewers = reviewers;
    }
}

