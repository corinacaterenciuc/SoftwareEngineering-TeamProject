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
    private String topic;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Proposal> presentations;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> participants;

    @NotNull
    private Integer room;
}
