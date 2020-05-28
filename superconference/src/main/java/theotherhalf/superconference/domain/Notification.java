package theotherhalf.superconference.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.springframework.data.relational.core.mapping.Table;

@Table 
@Entity
public class Notification extends BaseEntity
{
    // ID from BaseEntity
    private String text;
    private String href;

    public Notification(String text, String href) {
        this.text = text;
        this.href = href;
    }

    public Notification() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }



}
