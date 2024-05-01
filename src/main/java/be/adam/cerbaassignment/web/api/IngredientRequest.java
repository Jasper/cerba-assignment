package be.adam.cerbaassignment.web.api;

import lombok.Value;

@Value
public class IngredientRequest {
    String name;
    String quantity;
}
