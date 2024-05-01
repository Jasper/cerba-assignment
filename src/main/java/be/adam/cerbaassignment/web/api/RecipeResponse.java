package be.adam.cerbaassignment.web.api;

import lombok.Data;

import java.util.List;

@Data
public class RecipeResponse {

    private Long id;
    private String name;
    private String description;
    private String instructions;
    private List<IngredientResponse> ingredients;
}

