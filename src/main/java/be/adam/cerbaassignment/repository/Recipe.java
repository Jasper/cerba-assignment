package be.adam.cerbaassignment.repository;

import be.adam.cerbaassignment.web.api.IngredientRequest;
import be.adam.cerbaassignment.web.api.RecipeRequest;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;

@Entity
@Table(name = "recipes")
@Getter
public class Recipe extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String instructions;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        if (this.ingredients == null) {
            return emptyList();
        }

        return ingredients.stream()
                .sorted(comparing(Ingredient::getName))
                .toList();
    }

    public static Recipe of(RecipeRequest recipeRequest) {
        Recipe recipe = new Recipe();
        recipe.name = recipeRequest.name();
        recipe.description = recipeRequest.description();
        recipe.instructions = recipeRequest.instructions();
        return recipe;
    }

    public void update(RecipeRequest recipeRequest) {
        this.name = recipeRequest.name();
        this.description = recipeRequest.description();
        this.instructions = recipeRequest.instructions();
    }

    public void addIngredient(IngredientRequest ingredientRequest) {
        Ingredient ingredient = Ingredient.of(ingredientRequest);
        ingredient.setRecipe(this);
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
        ingredient.setRecipe(null);
    }
}
