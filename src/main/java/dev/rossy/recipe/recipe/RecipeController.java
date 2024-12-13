package dev.rossy.recipe.recipe;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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


    //post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Recipe recipe){
        jdbcClientRecipeRepository.create(recipe);
    }

    //put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Recipe recipe, @PathVariable Integer id){
        recipeRepository.save(recipe);
    }

    //delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        recipeRepository.delete(recipeRepository.findById(id).get());
    }


}

