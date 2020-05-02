package theotherhalf.superconference.domain;

import java.util.List;

public class Section extends BaseEntity
{
    private User chair;
    private String topic;
    private List<Proposal> presentations;
    private List<User> participants;
    private Integer room;
}
