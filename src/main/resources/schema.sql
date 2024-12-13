CREATE TABLE IF NOT EXISTS recipe (
  "id" bigint,
  "name" text,
  "ingredients" text,
  "instructions" text,
  "prep_time_minutes" bigint,
  "cook_time_minutes" bigint,
  "servings" bigint,
  "difficulty" text,
  "cuisine" text,
  "calories_per_serving" bigint,
  "tags" text,
  "user_id" bigint,
  "image" text,
  "rating" double precision,
  "review_count" bigint,
  "meal_type" text
);