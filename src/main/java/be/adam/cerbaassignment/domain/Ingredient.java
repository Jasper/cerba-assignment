package be.adam.cerbaassignment.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Ingredient extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String quantity;
    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

}
