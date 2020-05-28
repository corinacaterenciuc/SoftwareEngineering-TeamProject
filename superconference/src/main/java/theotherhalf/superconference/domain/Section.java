package theotherhalf.superconference.domain;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.data.relational.core.mapping.Table;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Table
@Entity
public class Section //extends BaseEntity
{
    @Id
    @Column(nullable = false)
    @NotNull
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)
    //@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private CMSUser chair;

    @ElementCollection
    private List<String> topic;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH})
    //@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Proposal> proposals;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    //@ManyToMany(cascade = CascadeType.ALL)
    private List<CMSUser> participants;

    private Integer room;

    public Section(CMSUser chair, @NotBlank List<String> topic, List<Proposal> proposals, List<CMSUser> participants, @NotNull Integer room) {
        this.chair = chair;
        this.topic = topic;
        this.proposals = proposals;
        this.participants = participants;
        this.room = room;
    }

    public Section()
    {
        this.chair = null;
        this.topic = null;
        this.proposals = new ArrayList<>();
        this.participants = new ArrayList<>();
        this.room = 0;
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getID()
    {
        return this.id;
    }
    public CMSUser getChair() {
        return chair;
    }

    public void setChair(CMSUser chair) {
        this.chair = chair;
    }

    public List<String> getTopic() {
        return topic;
    }

    public void setTopic(List<String> topic) {
        this.topic = topic;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public List<CMSUser> getParticipants() {
        return participants;
    }

    public void setParticipants(List<CMSUser> participants) {
        this.participants = participants;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    @Transactional
    public void addParticipant(CMSUser usr)
    {
        if(this.participants.contains(usr))
        {
            throw new ValidationException("[ERROR] CMSUser is already a participant");
        }

        this.participants.add(usr);
    }

    @Transactional
    public void removeParticipant(CMSUser usr)
    {
        if(!this.participants.contains(usr))
        {
            throw new ValidationException("[ERROR] CMSUser was not a participant");
        }
        this.participants = this.participants.stream().filter(x-> !x.getEmail().equals(usr.getEmail())).collect(Collectors.toList());
    }

    @Transactional
    public Proposal addProposal(Proposal proposal)
    {
        if(this.proposals.add(proposal))
        {
            return proposal;
        }
        throw new ValidationException("[ERROR] Proposal couldn't be added");
    }

    @Transactional
    public Proposal updateProposal(Proposal proposal)
    {
        Proposal oldProposal = this.getProposal(proposal.getID());
        if(null != proposal.getProposalName())
        {
            oldProposal.setProposalName(proposal.getProposalName());
        }
        if(null != proposal.getAuthor())
        {
            oldProposal.setAuthor(proposal.getAuthor());
        }
        if(null != proposal.getAbstractDescription())
        {
            oldProposal.setAbstractDescription(proposal.getAbstractDescription());
        }
        if(null != proposal.getBiddingPeople())
        {
            oldProposal.setBiddingPeople(proposal.getBiddingPeople());
        }
        if(null != proposal.getCoAuthors())
        {
            oldProposal.setCoAuthors(proposal.getCoAuthors());
        }
        if(null != proposal.getFilePath())
        {
            oldProposal.setFilePath(proposal.getFilePath());
        }
        if(null != proposal.getKeywords())
        {
            oldProposal.setKeywords(proposal.getKeywords());
        }
        if(null != proposal.getReviewers())
        {
            oldProposal.setReviewers(proposal.getReviewers());
        }
        if(null != proposal.getReviews())
        {
            oldProposal.setReviews(proposal.getReviews());
        }
        if(null != proposal.getTopics())
        {
            oldProposal.setTopics(proposal.getTopics());
        }
        return oldProposal;
    }

    @Transactional
    public void removeProposal(Long proposalId)
    {
        if(!this.hasProposal(proposalId))
        {
            throw new ValidationException("[ERROR] Proposal doesn't exist");
        }
        this.proposals = this.proposals.stream().filter(x-> !x.getID().equals(proposalId)).collect(Collectors.toList());
    }

    public boolean hasProposal(Long proposalId)
    {
        Optional<Proposal> optional = this.proposals.stream().filter(x -> x.getID().equals(proposalId)).findFirst();
        return optional.isPresent();
    }
    public Proposal getProposal(Long proposalId)
    {
        Optional<Proposal> optional = this.proposals.stream().filter(x -> x.getID().equals(proposalId)).findFirst();
        return optional.get();
    }

    public boolean hasParticipant(String email)
    {
        Optional<CMSUser> optional = this.participants.stream().filter(x -> x.getEmail().equals(email)).findFirst();
        return optional.isPresent();
    }
    public CMSUser getParticipant(String email)
    {
        Optional<CMSUser> optional = this.participants.stream().filter(x -> x.getEmail().equals(email)).findFirst();
        return optional.get();
    }
}
