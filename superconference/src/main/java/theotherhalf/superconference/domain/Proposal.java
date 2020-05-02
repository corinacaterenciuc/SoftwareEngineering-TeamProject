package theotherhalf.superconference.domain;

import java.util.List;

public class Proposal extends BaseEntity
{
    private String proposalName;

    private String filePath;
    private String abstractPath;

    private List<String> topics;
    private List<String> keywords;

    private User author;
    private List<User> coAuthors;

    private List<User> biddingPeople;
    private List<User> reviewers;
    private List<Review> reviews;

}
