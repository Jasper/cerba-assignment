package be.adam.cerbaassignment.web.api;

import lombok.Data;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;

@Data
public class RecipeResponse {

    Long id;
    String name;
    String description;
    String instructions;
    List<IngredientResponse> ingredients;

    public List<IngredientResponse> getIngredients() {
        if (this.ingredients == null) {
            return emptyList();
        }

        return this.ingredients.stream()
                .sorted(comparing(IngredientResponse::getName))
                .toList();
    }
}

