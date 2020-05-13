package theotherhalf.superconference.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Notification extends BaseEntity
{
    // ID from BaseEntity
    @NotBlank
    private String body;
}
