package theotherhalf.superconference.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Section extends BaseEntity
{
    @ManyToOne(cascade = CascadeType.ALL)
    private User chair;

    @NotBlank
    @ElementCollection
    private List<String> topic;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Proposal> proposals;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> participants;

    @NotNull
    private Integer room;

    public Section(User chair, @NotBlank List<String> topic, List<Proposal> proposals, List<User> participants, @NotNull Integer room) {
        this.chair = chair;
        this.topic = topic;
        this.proposals = proposals;
        this.participants = participants;
        this.room = room;
    }

    public Section() {
    }

    public User getChair() {
        return chair;
    }

    public void setChair(User chair) {
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

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

}
