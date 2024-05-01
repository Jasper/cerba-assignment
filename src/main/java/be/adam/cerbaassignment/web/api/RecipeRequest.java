package be.adam.cerbaassignment.web.api;

import lombok.Value;

@Value
public class RecipeRequest {

    String name;
    String description;
    String instructions;
}
