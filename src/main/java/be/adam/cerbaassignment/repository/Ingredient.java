package be.adam.cerbaassignment.repository;

import be.adam.cerbaassignment.web.api.IngredientRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ingredients")
@Getter
public class Ingredient extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String quantity;
    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    @Setter
    private Recipe recipe;

    public static Ingredient of(IngredientRequest ingredientRequest) {
        Ingredient ingredient = new Ingredient();
        ingredient.name = ingredientRequest.name();
        ingredient.quantity = ingredientRequest.quantity();
        return ingredient;
    }

}
