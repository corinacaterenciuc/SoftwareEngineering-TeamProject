package theotherhalf.superconference.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Conference extends BaseEntity
{
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Date zeroDeadline;

    private Date abstractDeadline;

    private Date proposalDeadline;

    private Date biddingDeadline;

    private Date evaluationDeadline;

    private Date presentationDeadline;

    @OneToMany(cascade = CascadeType.ALL)
    //@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Section> sections;

    public Conference()
    {
        this.initializeSections();
    }

    @Transactional
    void initializeSections()
    {
        this.sections = new ArrayList<>();
        Section main = new Section();
        main.setId(new Random().nextLong());
        this.sections.add(main);
    }

    public Conference(@NotBlank String name,
                      @NotBlank String description,
                      @NotNull Date zeroDeadline,
                      Date abstractDeadline,
                      Date proposalDeadline,
                      Date biddingDeadline,
                      Date evaluationDeadline,
                      Date presentationDeadline) {
        this.name = name;
        this.description = description;
        this.zeroDeadline = zeroDeadline;
        this.abstractDeadline = abstractDeadline;
        this.proposalDeadline = proposalDeadline;
        this.biddingDeadline = biddingDeadline;
        this.evaluationDeadline = evaluationDeadline;
        this.presentationDeadline = presentationDeadline;
        this.initializeSections();
    }


    public Conference(
            @NotBlank String name,
            @NotBlank String description,
            @NotNull Date zeroDeadline,
            Date abstractDeadline,
            Date proposalDeadline,
            Date biddingDeadline,
            Date evaluationDeadline,
            Date presentationDeadline,
            List<Section> sections) {
        this.name = name;
        this.description = description;
        this.zeroDeadline = zeroDeadline;
        this.abstractDeadline = abstractDeadline;
        this.proposalDeadline = proposalDeadline;
        this.biddingDeadline = biddingDeadline;
        this.evaluationDeadline = evaluationDeadline;
        this.presentationDeadline = presentationDeadline;
        this.sections = sections;
        this.sections.add(new Section());
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

    public List<Section> getSections()
    {
        if (this.hasSections())
        {
            return this.sections;
        }
        return null;
    }

    public Date getEvaluationDeadline() {
        return evaluationDeadline;
    }

    public void setEvaluationDeadline(Date evaluationDeadline) {
        this.evaluationDeadline = evaluationDeadline;
    }

    @Transactional
    public void setSections(List<Section> sections)
    {
        this.sections = sections;
    }

    @Transactional
    public Section addSection(Section section)
    {
        if(this.sections.add(section))
        {
            return section;
        }
        else
        {
            throw new ValidationException("[ERROR] Section couldn't be added");
        }
    }

    public boolean hasThisSection(Long sectionID)
    {
        return this.sections.stream().anyMatch(x-> x.getID().equals(sectionID));
    }

    @Transactional
    public void updateSection(Section section)
    {
        if(!this.hasThisSection(section.getID()))
        {
            throw new ValidationException("[ERROR] Section doesn't exist");
        }
        Section oldSection = this.sections.stream().filter(x -> x.getID().equals(section.getID())).findFirst().get();
        if(null != section.getChair())
        {
            oldSection.setChair(section.getChair());
        }
        if(null != section.getProposals())
        {
            oldSection.setProposals(section.getProposals());
        }
        if(null != section.getParticipants())
        {
            oldSection.setParticipants(section.getParticipants());
        }
        if(null != section.getTopic())
        {
            oldSection.setTopic(section.getTopic());
        }
        if(null != section.getRoom())
        {
            oldSection.setRoom(section.getRoom());
        }
    }

    @Transactional
    public void removeSection(Long sectionID)
    {
        this.sections = this.sections.stream().filter(x-> !x.getID().equals(sectionID)).collect(Collectors.toList());
    }

    public boolean hasSections()
    {
        return ! (this.sections.size() == 1);
    }

    public Section getSectionById(Long sectionId)
    {
        return this.sections.stream().filter(x -> x.getID().equals(sectionId)).findFirst().get();
    }

    public void addParticipantToConference(CMSUser usr)
    {
        Section main = this.getDefaultSection();
        if(!main.hasParticipant(usr.getEmail()))
        {
            main.addParticipant(usr);
        }

    }

    public void removeParticipantFromConference(CMSUser usr)
    {
        Section main = this.getDefaultSection();
        if(!main.hasParticipant(usr.getEmail()))
        {
            throw new ValidationException("[ERROR] Participant doesn't exist");
        }
        main.removeParticipant(usr);
    }

    public Section getDefaultSection()
    {
        Optional<Section> defau = this.sections.stream().filter(x -> x.getChair() == null).findFirst();
        if(defau.isPresent())
        {
            return defau.get();
        }
        else
        {
            throw new ValidationException("[ERROR] Something really bad happened");
        }
    }

    @Transactional
    public Review addReviewToProposal(Review review, Long proposalId)
    {
        Section main = this.getDefaultSection();
        return main.getProposal(proposalId).addReview(review);
    }

    public Proposal addProposal(Proposal proposal)
    {
        Section main = this.getDefaultSection();
        if(main.hasProposal(proposal.getID()))
        {
            throw new ValidationException("[ERROR] Proposal already exists");
        }

        return main.addProposal(proposal);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Proposal updateProposal(Proposal proposal)
    {
        Section main = this.getDefaultSection();
        if(!main.hasProposal(proposal.getID()))
        {
            throw new ValidationException("[ERROR] Proposal doesn't exist");
        }
        return main.updateProposal(proposal);
    }
    public void removeProposal(Long proposalId)
    {
        Section main = this.getDefaultSection();
        if(!main.hasProposal(proposalId))
        {
            throw new ValidationException("[ERROR] Proposal doesn't exist");
        }
        main.removeProposal(proposalId);
    }

    public List<CMSUser> getParticipants()
    {
        List<CMSUser> allParticipants = new ArrayList<>();
        this.sections.forEach(x -> allParticipants.addAll(x.getParticipants()));
        return allParticipants.stream().distinct().collect(Collectors.toList());
    }

    @Transactional
    public void removeAllParticipants()
    {
        this.sections.forEach(x -> x.setParticipants(new ArrayList<>()));
    }
}
