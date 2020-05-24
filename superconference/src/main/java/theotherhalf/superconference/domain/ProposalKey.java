package theotherhalf.superconference.domain;

public class ProposalKey
{
    private String email;
    private String title;
    private Long confId;

    public ProposalKey() {
    }

    public ProposalKey(String email, String title, Long confId) {
        this.email = email;
        this.title = title;
        this.confId = confId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getConfId() {
        return confId;
    }

    public void setConfId(Long confId) {
        this.confId = confId;
    }
}
