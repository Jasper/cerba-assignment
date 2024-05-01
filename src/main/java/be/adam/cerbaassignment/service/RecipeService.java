package be.adam.cerbaassignment.service;

import be.adam.cerbaassignment.repository.IngredientRepository;
import be.adam.cerbaassignment.repository.Recipe;
import be.adam.cerbaassignment.repository.RecipeRepository;
import be.adam.cerbaassignment.web.api.IngredientRequest;
import be.adam.cerbaassignment.web.api.RecipeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public List<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipe(Long recipeId) {
        return recipeRepository.getReferenceById(recipeId);
    }

    public Recipe createRecipe(RecipeRequest recipeRequest) {
        var recipe = Recipe.of(recipeRequest);

        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Long recipeId, RecipeRequest recipeRequest) {
        var recipeToUpdate = recipeRepository.getReferenceById(recipeId);
        recipeToUpdate.update(recipeRequest);

        return recipeRepository.save(recipeToUpdate);
    }

    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    public Recipe addIngredient(Long recipeId, IngredientRequest ingredientRequest) {
        var recipeToUpdate = recipeRepository.getReferenceById(recipeId);
        recipeToUpdate.addIngredient(ingredientRequest);

        return recipeRepository.save(recipeToUpdate);
    }

    public Recipe removeIngredient(Long recipeId, Long ingredientId) {
        var recipeToUpdate = recipeRepository.getReferenceById(recipeId);
        var ingredientToRemove = ingredientRepository.getReferenceById(ingredientId);
        recipeToUpdate.removeIngredient(ingredientToRemove);

        return recipeRepository.save(recipeToUpdate);
    }
}
