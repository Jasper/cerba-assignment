package be.adam.cerbaassignment.repository;

import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class RecipeFixture {

    public static Long ID = 1L;
    public static String NAME = "Recipe A";
    public static String DESCRIPTION = "Test Description";
    public static String INSTRUCTIONS = "Test Instructions";
    public static List<Ingredient> INGREDIENTS =
            List.of(
                    IngredientFixture.create()
                            .id(3L)
                            .name("Ingredient C")
                            .build(),
                    IngredientFixture.create()
                            .id(2L)
                            .name("Ingredient B")
                            .build(),
                    IngredientFixture.create().build()
            );

    private Long id = ID;
    private String name = NAME;
    private String description = DESCRIPTION;
    private String instructions = INSTRUCTIONS;
    private List<Ingredient> ingredients = INGREDIENTS;

    public static RecipeFixture create() {
        return new RecipeFixture();
    }

    public RecipeFixture id(Long id) {
        this.id = id;
        return this;
    }

    public RecipeFixture name(String name) {
        this.name = name;
        return this;
    }


    public RecipeFixture description(String description) {
        this.description = description;
        return this;
    }

    public RecipeFixture instructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public RecipeFixture ingredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public Recipe build() {
        Recipe recipe = new Recipe();
        ReflectionTestUtils.setField(recipe, "id", id);
        ReflectionTestUtils.setField(recipe, "name", name);
        ReflectionTestUtils.setField(recipe, "description", description);
        ReflectionTestUtils.setField(recipe, "instructions", instructions);
        ReflectionTestUtils.setField(recipe, "ingredients", ingredients);
        return recipe;
    }
}
