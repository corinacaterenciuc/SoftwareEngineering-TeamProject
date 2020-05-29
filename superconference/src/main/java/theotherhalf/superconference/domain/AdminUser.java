package theotherhalf.superconference.domain;

import javax.persistence.Entity;

@Entity
public class AdminUser extends BaseEntity
{
    private String email;
    private boolean boss;

    public AdminUser(String email, boolean boss) {
        this.email = email;
        this.boss = boss;
    }

    public AdminUser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBoss() {
        return boss;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }
}
