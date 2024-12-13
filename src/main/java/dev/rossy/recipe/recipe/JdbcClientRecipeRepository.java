package dev.rossy.recipe.recipe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRecipeRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcClientRecipeRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientRecipeRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Recipe> findAll() {
        return jdbcClient.sql("SELECT * FROM recipe")
                .query(Recipe.class)
                .list();
    }

    public Optional<Recipe> findById(Integer id) {
        return jdbcClient.sql("""
                SELECT id, name, ingredients, instructions, prep_time_minutes, cook_time_minutes,
                       servings, difficulty, cuisine, calories_per_serving, tags, user_id, image,
                       rating, review_count, meal_type
                FROM recipe
                WHERE id = :id
                """)
                .param("id", id)
                .query(Recipe.class)
                .optional();
    }

    public void create(Recipe recipe) {
        var updated = jdbcClient.sql("""
                INSERT INTO recipe (
                    id, name, ingredients, instructions, prep_time_minutes, cook_time_minutes,
                    servings, difficulty, cuisine, calories_per_serving, tags, user_id, image,
                    rating, review_count, meal_type
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """)
                .params(List.of(
                        recipe.id(),
                        recipe.name(),
                        recipe.ingredients().toString(),
                        recipe.instructions().toString(),
                        recipe.prep_time_minutes(),
                        recipe.cook_time_minutes(),
                        recipe.servings(),
                        recipe.difficulty(),
                        recipe.cuisine(),
                        recipe.calories_per_serving(),
                        recipe.tags().toString(),
                        recipe.user_id(),
                        recipe.image(),
                        recipe.rating(),
                        recipe.review_count(),
                        recipe.meal_type().toString()
                ))
                .update();

        Assert.state(updated == 1, "Failed to create recipe " + recipe.name());
    }


    public int count() {
        return (int) jdbcClient.sql("SELECT COUNT(*) FROM recipe").query().singleValue();
    }


    public void saveAll(List<Recipe> recipes) {
        recipes.forEach(this::create);
    }

    public List<Recipe> findByCuisine(String cuisine) {
        return jdbcClient.sql("SELECT * FROM recipe WHERE cuisine = :cuisine")
                .param("cuisine", cuisine)
                .query(Recipe.class)
                .list();
    }

    public List<Recipe> findByTags(String tag) {
        return jdbcClient.sql("SELECT * FROM recipe WHERE tags LIKE :tag")
                .param("tag", "%" + tag + "%")
                .query(Recipe.class)
                .list();
    }
}
