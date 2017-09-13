package pc.springframework.spring5recipeapp.converters;

import org.junit.Before;
import org.junit.Test;
import pc.springframework.spring5recipeapp.commands.RecipeCommand;
import pc.springframework.spring5recipeapp.domain.Category;
import pc.springframework.spring5recipeapp.domain.Ingredient;
import pc.springframework.spring5recipeapp.domain.Notes;
import pc.springframework.spring5recipeapp.domain.Recipe;
import pc.springframework.spring5recipeapp.enums.Difficulty;
import pc.springframework.spring5recipeapp.helperfunctions.FilesHelper;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {

    private static final Long RECIPE_ID = 1L;
    private static final String RECIPE_DESCRIPTION = "My Recipe";
    private static final Integer RECIPE_PREP_TIME = Integer.valueOf("7");
    private static final Integer RECIPE_COOK_TIME = Integer.valueOf("5");
    private static final Integer RECIPE_SERVINGS = Integer.valueOf("3");
    private static final String RECIPE_SOURCE = "Source";
    private static final String RECIPE_URL = "Some URL";
    private static final String RECIPE_DIRECTIONS = "Directions";
    private static final Difficulty RECIPE_DIFFICULTY = Difficulty.EASY;
    private static final String RECIPE_FAKE_IMAGE_TXT = "fake image text";

    private static final Long CAT_ID_1 = 1L;
    private static final Long CAT_ID2 = 2L;
    private static final Long INGRED_ID_1 = 3L;
    private static final Long INGRED_ID_2 = 4L;
    private static final Long NOTES_ID = 9L;

    private RecipeToRecipeCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeToRecipeCommand(new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new NotesToNotesCommand());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void convert() throws Exception {//given
        Category category = new Category();
        category.setId(CAT_ID_1);
        Category category2 = new Category();
        category2.setId(CAT_ID2);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID_1);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED_ID_2);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);

        Byte[] recipeImage = FilesHelper.byteToObject(RECIPE_FAKE_IMAGE_TXT.getBytes());


        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setDescription(RECIPE_DESCRIPTION);
        recipe.setPrepTime(RECIPE_PREP_TIME);
        recipe.setCookTime(RECIPE_COOK_TIME);
        recipe.setServings(RECIPE_SERVINGS);
        recipe.setSource(RECIPE_SOURCE);
        recipe.setUrl(RECIPE_URL);
        recipe.setDirections(RECIPE_DIRECTIONS);
        recipe.setDifficulty(RECIPE_DIFFICULTY);
        recipe.setNotes(notes);
        recipe.setImage(recipeImage);
        recipe.addCategory(category);
        recipe.addCategory(category2);
        recipe.addIngredient(ingredient);
        recipe.addIngredient(ingredient2);

        //when
        RecipeCommand recipeCommand = converter.convert(recipe);

        //then
        assertEquals(RECIPE_ID, recipeCommand.getId());
        assertEquals(RECIPE_DESCRIPTION, recipeCommand.getDescription());
        assertEquals(RECIPE_PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(RECIPE_COOK_TIME, recipeCommand.getCookTime());
        assertEquals(RECIPE_SERVINGS, recipeCommand.getServings());
        assertEquals(RECIPE_SOURCE, recipeCommand.getSource());
        assertEquals(RECIPE_URL, recipeCommand.getUrl());
        assertEquals(RECIPE_DIRECTIONS, recipeCommand.getDirections());
        assertEquals(RECIPE_DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(NOTES_ID, recipeCommand.getNotes().getId());
        assertEquals(recipeImage, recipeCommand.getImage());
        assertEquals(2, recipeCommand.getCategories().size());
        assertEquals(2, recipeCommand.getIngredients().size());
    }

}