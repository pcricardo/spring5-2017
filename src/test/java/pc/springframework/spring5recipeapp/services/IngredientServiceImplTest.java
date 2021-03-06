package pc.springframework.spring5recipeapp.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pc.springframework.spring5recipeapp.commands.IngredientCommand;
import pc.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import pc.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import pc.springframework.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import pc.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import pc.springframework.spring5recipeapp.domain.Ingredient;
import pc.springframework.spring5recipeapp.domain.Recipe;
import pc.springframework.spring5recipeapp.repositories.IngredientRepository;
import pc.springframework.spring5recipeapp.repositories.RecipeRepository;
import pc.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    private IngredientService ingredientService;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientService = new IngredientServiceImpl(recipeRepository,
                unitOfMeasureRepository,
                ingredientRepository,
                ingredientToIngredientCommand,
                ingredientCommandToIngredient
        );
    }

    @Test
    public void findByRecipeIdAndIngredientId() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(1L)).thenReturn(recipeOptional);

        //when
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //then
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void saveIngredientCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    public void deleteById() throws Exception {
        //given
        Long idRecipe = 1L;
        Long idToDelete = 2L;

        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(idToDelete);
        recipe.addIngredient(ingredient);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        //when
        ingredientService.deleteById(idRecipe, idToDelete);

        //then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
        verify(ingredientRepository, times(1)).deleteById(anyLong());
    }

}