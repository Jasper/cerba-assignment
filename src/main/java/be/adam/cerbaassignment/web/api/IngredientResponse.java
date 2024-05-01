package be.adam.cerbaassignment.web.api;

import lombok.Data;

@Data
public final class IngredientResponse {

    private Long id;
    private String name;
    private String quantity;
}
