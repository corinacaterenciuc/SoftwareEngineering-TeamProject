package theotherhalf.superconference.domain;

public class Review extends BaseEntity
{
    private User user;
    private Proposal proposal;
    private ENUMERATION_GRADES grade;
    private String justification;
}
