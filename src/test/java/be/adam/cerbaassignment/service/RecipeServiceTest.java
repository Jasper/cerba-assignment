package be.adam.cerbaassignment.service;

import be.adam.cerbaassignment.repository.*;
import be.adam.cerbaassignment.web.api.IngredientRequest;
import be.adam.cerbaassignment.web.api.RecipeRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    public void getRecipes() {
        var recipe = RecipeFixture.create().build();
        var recipes = List.of(recipe);

        when(recipeRepository.findAll()).thenReturn(recipes);

        var result = recipeService.getRecipes();

        assertEquals(1, result.size());
        assertEquals(recipe, result.get(0));
    }

    @Test
    public void getRecipe() {
        var recipe = RecipeFixture.create().build();

        when(recipeRepository.getReferenceById(recipe.getId()))
                .thenReturn(recipe);

        var result = recipeService.getRecipe(recipe.getId());

        assertEquals(recipe.getId(), result.getId());
        assertEquals(recipe.getName(), result.getName());
        assertEquals(recipe.getDescription(), result.getDescription());
        assertEquals(recipe.getInstructions(), result.getInstructions());

        verify(recipeRepository).getReferenceById(recipe.getId());
    }

    @Test
    public void createRecipe() {
        var request = new RecipeRequest("Recept A", "Beschrijving", "instructions");

        recipeService.createRecipe(request);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeRepository).save(argumentCaptor.capture());
        var recipe = argumentCaptor.getValue();

        assertEquals(request.getName(), recipe.getName());
        assertEquals(request.getDescription(), recipe.getDescription());
        assertEquals(request.getInstructions(), recipe.getInstructions());
    }

    @Test
    public void updateRecipe() {
        var recipe = RecipeFixture.create().build();
        var request = new RecipeRequest("Recept B", "updated", "updated");

        when(recipeRepository.getReferenceById(recipe.getId())).thenReturn(recipe);

        recipeService.updateRecipe(recipe.getId(), request);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeRepository).save(argumentCaptor.capture());
        var updatedRecipe = argumentCaptor.getValue();

        assertEquals(recipe.getId(), updatedRecipe.getId());
        assertEquals(request.getName(), updatedRecipe.getName());
        assertEquals(request.getDescription(), updatedRecipe.getDescription());
        assertEquals(request.getInstructions(), updatedRecipe.getInstructions());
    }

    @Test
    public void addIngredient() {
        var recipe = RecipeFixture.create().ingredients(new ArrayList<>()).build();
        var request = new IngredientRequest("suiker", "100g");

        when(recipeRepository.getReferenceById(recipe.getId())).thenReturn(recipe);

        recipeService.addIngredient(recipe.getId(), request);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeRepository).save(argumentCaptor.capture());
        var updatedRecipe = argumentCaptor.getValue();

        assertEquals(recipe.getId(), updatedRecipe.getId());
        assertEquals(recipe.getName(), updatedRecipe.getName());
        assertEquals(recipe.getDescription(), updatedRecipe.getDescription());
        assertEquals(recipe.getInstructions(), updatedRecipe.getInstructions());
        assertEquals(1, updatedRecipe.getIngredients().size());
        assertEquals(request.getName(), updatedRecipe.getIngredients().get(0).getName());
        assertEquals(request.getQuantity(), updatedRecipe.getIngredients().get(0).getQuantity());
    }

    @Test
    public void removeIngredient() {
        var ingredient = IngredientFixture.create().build();
        var ingredients = new ArrayList<Ingredient>();
        ingredients.add(ingredient);
        var recipe = RecipeFixture.create().ingredients(ingredients).build();

        when(recipeRepository.getReferenceById(recipe.getId())).thenReturn(recipe);
        when(ingredientRepository.getReferenceById(ingredient.getId())).thenReturn(ingredient);

        recipeService.removeIngredient(recipe.getId(), ingredient.getId());

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeRepository).save(argumentCaptor.capture());
        var updatedRecipe = argumentCaptor.getValue();

        assertEquals(recipe.getId(), updatedRecipe.getId());
        assertEquals(recipe.getName(), updatedRecipe.getName());
        assertEquals(recipe.getDescription(), updatedRecipe.getDescription());
        assertEquals(recipe.getInstructions(), updatedRecipe.getInstructions());
        assertEquals(0, updatedRecipe.getIngredients().size());
    }

    @Test
    public void deleteRecipe() {
        var recipeId = RecipeFixture.ID;

        recipeService.deleteRecipe(recipeId);

        verify(recipeRepository).deleteById(recipeId);
    }
}
