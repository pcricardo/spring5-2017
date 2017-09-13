package pc.springframework.spring5recipeapp.converters;

import org.junit.Before;
import org.junit.Test;
import pc.springframework.spring5recipeapp.commands.CategoryCommand;
import pc.springframework.spring5recipeapp.commands.IngredientCommand;
import pc.springframework.spring5recipeapp.commands.NotesCommand;
import pc.springframework.spring5recipeapp.commands.RecipeCommand;
import pc.springframework.spring5recipeapp.domain.Recipe;
import pc.springframework.spring5recipeapp.enums.Difficulty;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {

    private static final Long RECIPE_ID = 1L;
    private static final String RECIPE_DESCRIPTION = "My Recipe";
    private static final Integer RECIPE_PREP_TIME = Integer.valueOf("7");
    private static final Integer RECIPE_COOK_TIME = Integer.valueOf("5");
    private static final Integer RECIPE_SERVINGS = Integer.valueOf("3");
    private static final String RECIPE_SOURCE = "Source";
    private static final String RECIPE_URL = "Some URL";
    private static final String RECIPE_DIRECTIONS = "Directions";
    private static final Difficulty RECIPE_DIFFICULTY = Difficulty.EASY;

    private static final Long CAT_ID_1 = 1L;
    private static final Long CAT_ID2 = 2L;
    private static final Long INGRED_ID_1 = 3L;
    private static final Long INGRED_ID_2 = 4L;
    private static final Long NOTES_ID = 9L;

    private RecipeCommandToRecipe converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        CategoryCommand category = new CategoryCommand();
        category.setId(CAT_ID_1);
        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CAT_ID2);

        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setId(INGRED_ID_1);
        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGRED_ID_2);

        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setDescription(RECIPE_DESCRIPTION);
        recipeCommand.setPrepTime(RECIPE_PREP_TIME);
        recipeCommand.setCookTime(RECIPE_COOK_TIME);
        recipeCommand.setServings(RECIPE_SERVINGS);
        recipeCommand.setSource(RECIPE_SOURCE);
        recipeCommand.setUrl(RECIPE_URL);
        recipeCommand.setDirections(RECIPE_DIRECTIONS);
        recipeCommand.setDifficulty(RECIPE_DIFFICULTY);
        recipeCommand.setNotes(notes);
        recipeCommand.addCategory(category);
        recipeCommand.addCategory(category2);
        recipeCommand.addIngredient(ingredient);
        recipeCommand.addIngredient(ingredient2);

        //when
        Recipe recipe  = converter.convert(recipeCommand);

        //then
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(RECIPE_DESCRIPTION, recipe.getDescription());
        assertEquals(RECIPE_PREP_TIME, recipe.getPrepTime());
        assertEquals(RECIPE_COOK_TIME, recipe.getCookTime());
        assertEquals(RECIPE_SERVINGS, recipe.getServings());
        assertEquals(RECIPE_SOURCE, recipe.getSource());
        assertEquals(RECIPE_URL, recipe.getUrl());
        assertEquals(RECIPE_DIRECTIONS, recipe.getDirections());
        assertEquals(RECIPE_DIFFICULTY, recipe.getDifficulty());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }

}