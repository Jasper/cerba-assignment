package be.adam.cerbaassignment.web;

import be.adam.cerbaassignment.repository.Recipe;
import be.adam.cerbaassignment.service.RecipeService;
import be.adam.cerbaassignment.web.api.IngredientRequest;
import be.adam.cerbaassignment.web.api.RecipeRequest;
import be.adam.cerbaassignment.web.api.RecipeResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Comparator.comparing;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<RecipeResponse> getAllRecipes() {
        return recipeService.getRecipes().stream()
                .sorted(comparing(Recipe::getName))
                .map(recipe -> modelMapper.map(recipe, RecipeResponse.class))
                .toList();
    }

    @GetMapping("/{recipeId}")
    public RecipeResponse getRecipe(@PathVariable Long recipeId) {
        var recipe = recipeService.getRecipe(recipeId);
        return modelMapper.map(recipe, RecipeResponse.class);
    }

    @PostMapping
    public RecipeResponse addRecipe(@RequestBody RecipeRequest recipeRequest) {
        var recipe = recipeService.createRecipe(recipeRequest);
        return modelMapper.map(recipe, RecipeResponse.class);
    }

    @PutMapping("/{recipeId}")
    public RecipeResponse updateRecipe(@PathVariable Long recipeId,
                                       @RequestBody RecipeRequest recipeRequest) {
        var recipe = recipeService.updateRecipe(recipeId, recipeRequest);
        return modelMapper.map(recipe, RecipeResponse.class);
    }

    @DeleteMapping("/{recipeId}")
    public void deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
    }

    @PostMapping("/{recipeId}/ingredients")
    public RecipeResponse addIngredient(@PathVariable Long recipeId,
                                        @RequestBody IngredientRequest ingredientRequest) {
        var recipe = recipeService.addIngredient(recipeId, ingredientRequest);
        return modelMapper.map(recipe, RecipeResponse.class);
    }

    @DeleteMapping("/{recipeId}/ingredients/{ingredientId}")
    public RecipeResponse removeIngredient(@PathVariable Long recipeId,
                                           @PathVariable Long ingredientId) {
        var recipe = recipeService.removeIngredient(recipeId, ingredientId);
        return modelMapper.map(recipe, RecipeResponse.class);
    }
}
