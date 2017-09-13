package pc.springframework.spring5recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pc.springframework.spring5recipeapp.enums.Difficulty;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Byte[] image;
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();

    public RecipeCommand addIngredient(IngredientCommand ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public RecipeCommand addCategory(CategoryCommand category) {
        this.categories.add(category);
        return this;
    }
}
