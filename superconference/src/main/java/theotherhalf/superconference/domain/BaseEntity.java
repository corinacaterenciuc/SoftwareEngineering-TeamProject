package theotherhalf.superconference.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity
{
    @Id
    @GeneratedValue
    @Column(name = "_id", nullable = false)
    @NotNull
    private Long ID;

    @Override
    public String toString()
    {
        return "BaseEntity{" + "ID=" + ID + '}';
    }

    public Long getID()
    {
        return ID;
    }

    public void setID(Long ID)
    {
        this.ID = ID;
    }
}
