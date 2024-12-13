package dev.rossy.recipe.recipe;

import org.springframework.data.repository.ListCrudRepository;

public interface RecipeRepository extends ListCrudRepository<Recipe, Integer> {

}
