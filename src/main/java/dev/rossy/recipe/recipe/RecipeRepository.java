package dev.rossy.recipe.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByCuisine(String cuisine);

    List<Recipe> findByTagsContaining(String tag); // Menggunakan LIKE secara otomatis
}
