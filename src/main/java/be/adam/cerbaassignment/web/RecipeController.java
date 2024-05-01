package be.adam.cerbaassignment.web;

import be.adam.cerbaassignment.service.RecipeService;
import be.adam.cerbaassignment.web.api.IngredientRequest;
import be.adam.cerbaassignment.web.api.RecipeRequest;
import be.adam.cerbaassignment.web.api.RecipeResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<RecipeResponse> getAllRecipes() {
        return recipeService.getRecipes().stream()
                .map(recipe -> modelMapper.map(recipe, RecipeResponse.class))
                .toList();
    }

    @GetMapping("/{recipeId}")
    public RecipeResponse getRecipe(@PathVariable Long recipeId) {
        return modelMapper.map(recipeService.getRecipe(recipeId), RecipeResponse.class);
    }

    @PostMapping
    public RecipeResponse addRecipe(@RequestBody RecipeRequest recipeRequest) {
        return modelMapper.map(recipeService.createRecipe(recipeRequest), RecipeResponse.class);
    }

    @PutMapping("/{recipeId}")
    public RecipeResponse updateRecipe(@PathVariable Long recipeId,
                                       @RequestBody RecipeRequest recipeRequest) {
        return modelMapper.map(recipeService.updateRecipe(recipeId, recipeRequest), RecipeResponse.class);
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
