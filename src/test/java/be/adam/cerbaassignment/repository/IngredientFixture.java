package be.adam.cerbaassignment.repository;

import org.springframework.test.util.ReflectionTestUtils;

public class IngredientFixture {

    public static Long ID = 1L;
    public static String NAME = "Ingredient A";
    public static String QUANTITY = "100G";

    private Long id = ID;
    private String name = NAME;
    private String quantity = QUANTITY;

    public static IngredientFixture create() {
        return new IngredientFixture();
    }

    public IngredientFixture id(Long id) {
        this.id = id;
        return this;
    }

    public IngredientFixture name(String name) {
        this.name = name;
        return this;
    }

    public Ingredient build() {
        Ingredient ingredient = new Ingredient();
        ReflectionTestUtils.setField(ingredient, "id", id);
        ReflectionTestUtils.setField(ingredient, "name", name);
        ReflectionTestUtils.setField(ingredient, "quantity", quantity);

        return ingredient;
    }
}

