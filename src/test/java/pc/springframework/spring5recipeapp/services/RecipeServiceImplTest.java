package pc.springframework.spring5recipeapp.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pc.springframework.spring5recipeapp.commands.RecipeCommand;
import pc.springframework.spring5recipeapp.converters.RecipeCommandToRecipe;
import pc.springframework.spring5recipeapp.converters.RecipeToRecipeCommand;
import pc.springframework.spring5recipeapp.domain.Recipe;
import pc.springframework.spring5recipeapp.exceptions.NotFoundException;
import pc.springframework.spring5recipeapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    private RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository,
                recipeCommandToRecipe,
                recipeToRecipeCommand);
    }


    @Test
    public void getRecipes() throws Exception {
        // GIVEN
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesData);

        // WHEN
        Set<Recipe> recipes = recipeService.getRecipes();

        // THEN
        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }


    @Test
    public void findById() throws Exception {
        // GIVEN
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // WHEN
        Recipe recipeReturned = recipeService.findById(1L);

        // THEN
        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();

    }

    @Test(expected = NotFoundException.class)
    public void findByIdWhenIdNotExistThenExpectNotFoundException() throws Exception{
        //given
        Optional<Recipe>recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        recipeService.findById(anyLong());

        //then
        fail("Exception Expected");

    }

    @Test
    public void findCommandById() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        //when
        RecipeCommand commandById = recipeService.findCommandById(1L);

        //then
        assertNotNull("Null recipe returned", commandById);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void saveRecipeCommand() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
        //given
        Long idToDelete = 1L;
        //no mockito 'when', since method has void return type

        //when
        recipeService.deleteById(idToDelete);

        //then
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }

}