package dev.rossy.recipe.recipe;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;

import java.util.List;

public record Recipe(
        @Id
        Integer id,
        @NotEmpty String name,
        List<String> ingredients, // Diubah menjadi List<String>
        List<String> instructions, // Diubah menjadi List<String>
        @Positive Long prep_time_minutes,
        @Positive Long cook_time_minutes,
        @Positive Long servings,
        @NotEmpty String difficulty,
        @NotEmpty String cuisine,
        @Positive Long calories_per_serving,
        List<String> tags,
        @Positive Long user_id,
        String image,
        @Positive Double rating,
        @Positive Long review_count,
        List<String> meal_type

) {}
