package be.adam.cerbaassignment.web;

import be.adam.cerbaassignment.repository.IngredientFixture;
import be.adam.cerbaassignment.repository.RecipeFixture;
import be.adam.cerbaassignment.service.RecipeService;
import be.adam.cerbaassignment.web.api.IngredientRequest;
import be.adam.cerbaassignment.web.api.RecipeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecipeControllerTest extends ControllerTest {

    @MockBean
    private RecipeService recipeService;

    @Test
    public void getAllRecipes() throws Exception {
        var recipeA = RecipeFixture.create().build();
        var recipeB = RecipeFixture.create().id(2L).name("Recipe B").build();

        when(recipeService.getRecipes()).thenReturn(List.of(recipeB, recipeA));

        mockMvc.perform(get(("/recipes")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(equalTo(recipeA.getId()), Long.class))
                .andExpect(jsonPath("$.[0].name", equalTo(recipeA.getName())))
                .andExpect(jsonPath("$.[0].description", equalTo(recipeA.getDescription())))
                .andExpect(jsonPath("$.[0].instructions", equalTo(recipeA.getInstructions())))
                .andExpect(jsonPath("$.[0].ingredients", hasSize(3)))
                .andExpect(jsonPath("$.[1].id").value(equalTo(recipeB.getId()), Long.class))
                .andExpect(jsonPath("$.[1].name", equalTo(recipeB.getName())))
                .andExpect(jsonPath("$.[1].description", equalTo(recipeB.getDescription())))
                .andExpect(jsonPath("$.[1].instructions", equalTo(recipeB.getInstructions())))
                .andExpect(jsonPath("$.[1].ingredients", hasSize(3)));

        verify(recipeService).getRecipes();
    }

    @Test
    public void getRecipe() throws Exception {
        var recipe = RecipeFixture.create().build();

        when(recipeService.getRecipe(recipe.getId())).thenReturn(recipe);

        mockMvc.perform(get("/recipes/{recipeId}", recipe.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(equalTo(recipe.getId()), Long.class))
                .andExpect(jsonPath("$.name", equalTo(recipe.getName())))
                .andExpect(jsonPath("$.description", equalTo(recipe.getDescription())))
                .andExpect(jsonPath("$.instructions", equalTo(recipe.getInstructions())))
                .andExpect(jsonPath("$.ingredients", hasSize(3)))
                .andExpect(jsonPath("$.ingredients[0].id").value(equalTo(1L), Long.class))
                .andExpect(jsonPath("$.ingredients[0].name", equalTo(IngredientFixture.NAME)))
                .andExpect(jsonPath("$.ingredients[0].quantity", equalTo(IngredientFixture.QUANTITY)))
                .andExpect(jsonPath("$.ingredients[1].id").value(equalTo(2L), Long.class))
                .andExpect(jsonPath("$.ingredients[1].name", equalTo("Ingredient B")))
                .andExpect(jsonPath("$.ingredients[2].id").value(equalTo(3L), Long.class))
                .andExpect(jsonPath("$.ingredients[2].name", equalTo("Ingredient C")));

        verify(recipeService).getRecipe(recipe.getId());
    }

    @Test
    public void addRecipe() throws Exception {
        var createRecipeRequest =
                new RecipeRequest(RecipeFixture.NAME, RecipeFixture.DESCRIPTION, RecipeFixture.INSTRUCTIONS);
        var recipe = RecipeFixture.create().ingredients(emptyList()).build();

        when(recipeService.createRecipe(createRecipeRequest)).thenReturn(recipe);

        mockMvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRecipeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(equalTo(recipe.getId()), Long.class))
                .andExpect(jsonPath("$.name", equalTo(recipe.getName())))
                .andExpect(jsonPath("$.description", equalTo(recipe.getDescription())))
                .andExpect(jsonPath("$.instructions", equalTo(recipe.getInstructions())))
                .andExpect(jsonPath("$.ingredients", hasSize(0)));

        verify(recipeService).createRecipe(createRecipeRequest);
    }

    @Test
    public void updateRecipe() throws Exception {
        var updateRecipeRequest =
                new RecipeRequest("updated name", "updated description", "updated instructions");
        var recipe = RecipeFixture.create()
                .name(updateRecipeRequest.getName())
                .description(updateRecipeRequest.getDescription())
                .instructions(updateRecipeRequest.getInstructions())
                .ingredients(emptyList())
                .build();

        when(recipeService.updateRecipe(RecipeFixture.ID, updateRecipeRequest)).thenReturn(recipe);

        mockMvc.perform(put("/recipes/{recipeId}", RecipeFixture.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRecipeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(equalTo(recipe.getId()), Long.class))
                .andExpect(jsonPath("$.name", equalTo(recipe.getName())))
                .andExpect(jsonPath("$.description", equalTo(recipe.getDescription())))
                .andExpect(jsonPath("$.instructions", equalTo(recipe.getInstructions())))
                .andExpect(jsonPath("$.ingredients", hasSize(0)));

        verify(recipeService).updateRecipe(RecipeFixture.ID, updateRecipeRequest);
    }

    @Test
    public void deleteRecipe() throws Exception {
        mockMvc.perform(delete("/recipes/{recipeId}", RecipeFixture.ID))
                .andExpect(status().isOk());

        verify(recipeService).deleteRecipe(RecipeFixture.ID);
    }

    @Test
    public void addIngredient() throws Exception {
        var ingredient = IngredientFixture.create().build();
        var ingredientRequest = new IngredientRequest(ingredient.getName(), ingredient.getQuantity());
        var recipe = RecipeFixture.create().ingredients(List.of(ingredient)).build();

        when(recipeService.addIngredient(RecipeFixture.ID, ingredientRequest)).thenReturn(recipe);

        mockMvc.perform(post("/recipes/{recipeId}/ingredients", RecipeFixture.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredientRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(equalTo(recipe.getId()), Long.class))
                .andExpect(jsonPath("$.name", equalTo(recipe.getName())))
                .andExpect(jsonPath("$.description", equalTo(recipe.getDescription())))
                .andExpect(jsonPath("$.instructions", equalTo(recipe.getInstructions())))
                .andExpect(jsonPath("$.ingredients", hasSize(1)))
                .andExpect(jsonPath("$.ingredients[0].id").value(equalTo(ingredient.getId()), Long.class))
                .andExpect(jsonPath("$.ingredients[0].name", equalTo(ingredient.getName())))
                .andExpect(jsonPath("$.ingredients[0].quantity", equalTo(ingredient.getQuantity())));

        verify(recipeService).addIngredient(RecipeFixture.ID, ingredientRequest);
    }

    @Test
    public void removeIngredient() throws Exception {
        var recipe = RecipeFixture.create().build();

        when(recipeService.removeIngredient(recipe.getId(), IngredientFixture.ID))
                .thenReturn(recipe);

        mockMvc.perform(delete("/recipes/{recipeId}/ingredients/{ingredientId}",
                        RecipeFixture.ID, IngredientFixture.ID))
                .andExpect(status().isOk());

        verify(recipeService).removeIngredient(RecipeFixture.ID, IngredientFixture.ID);
    }
}
