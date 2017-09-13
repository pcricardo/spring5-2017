package pc.springframework.spring5recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pc.springframework.spring5recipeapp.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
