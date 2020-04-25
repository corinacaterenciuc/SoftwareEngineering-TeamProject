package otherhalf.superconference.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sections")
public class Section
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private Conference conference;
}
