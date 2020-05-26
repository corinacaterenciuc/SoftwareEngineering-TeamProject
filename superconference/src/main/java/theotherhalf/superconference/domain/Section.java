package theotherhalf.superconference.domain;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Section extends BaseEntity
{
    @ManyToOne(cascade = CascadeType.ALL)
    private User chair;

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

    public Section()
    {
        this.chair = null;
        this.topic = null;
        this.proposals = null;
        this.participants = null;
        this.room = 0;
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

    @Transactional
    public void addParticipant(User usr)
    {
        if(this.participants.contains(usr))
        {
            throw new ValidationException("[ERROR] User is already a participant");
        }

        this.participants.add(usr);
    }

    @Transactional
    public void removeParticipant(User usr)
    {
        if(!this.participants.contains(usr))
        {
            throw new ValidationException("[ERROR] User was not a participant");
        }
        this.participants = this.participants.stream().filter(x-> !x.getEmail().equals(usr.getEmail())).collect(Collectors.toList());
        this.participants.remove(usr);
    }
}
