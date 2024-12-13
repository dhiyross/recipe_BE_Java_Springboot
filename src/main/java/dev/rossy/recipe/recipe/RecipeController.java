package dev.rossy.recipe.recipe;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final JdbcClientRecipeRepository jdbcClientRecipeRepository;

    public RecipeController(RecipeRepository recipeRepository, JdbcClientRecipeRepository jdbcClientRecipeRepository) {
        this.recipeRepository = recipeRepository;
        this.jdbcClientRecipeRepository = jdbcClientRecipeRepository;
    }
    @GetMapping("")
    List<Recipe> findAll(){
        return recipeRepository.findAll();
    }

    @GetMapping("/{id}")
    Recipe findById(@PathVariable Integer id){
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty()){
            throw new RecipeNotFoundException();
        }
        return recipe.get();
    }

    @GetMapping("/search")
    List<Recipe> findByTag(@RequestParam String tag) {
        return jdbcClientRecipeRepository.findByTags(tag);
    }

    @GetMapping("/by-cuisine")
    List<Recipe> findByCuisine(@RequestParam String cuisine) {
        return jdbcClientRecipeRepository.findByCuisine(cuisine);
    }


    // POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    ResponseEntity<String> create(@Valid @RequestBody Recipe recipe) {
        jdbcClientRecipeRepository.create(recipe);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Recipe '" + recipe.name() + "' with ID " + recipe.id() + " created successfully!");
    }

    // PUT
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    ResponseEntity<String> update(@Valid @RequestBody Recipe recipe, @PathVariable Integer id) {
        Optional<Recipe> existingRecipe = recipeRepository.findById(id);

        if (existingRecipe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Recipe with ID " + id + " not found!");
        }

        recipeRepository.save(recipe);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Recipe '" + existingRecipe.get().name() + "' with ID " + id + " updated successfully!");
    }

    // DELETE
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable Integer id) {
        Optional<Recipe> existingRecipe = recipeRepository.findById(id);

        if (existingRecipe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Recipe with ID " + id + " not found!");
        }

        String recipeName = existingRecipe.get().name();
        recipeRepository.delete(existingRecipe.get());

        return ResponseEntity.status(HttpStatus.OK)
                .body("Recipe '" + recipeName + "' with ID " + id + " deleted successfully!");
    }

}

