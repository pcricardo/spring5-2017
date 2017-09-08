package pc.springframework.spring5recipeapp.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import pc.springframework.spring5recipeapp.domain.Recipe;
import pc.springframework.spring5recipeapp.services.RecipeService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {
    private IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipeapp/index"));
    }

    @Test
    public void getIndexPage() throws Exception {
        // GIVEN
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipesData);

        // WHEN
        String viewName = indexController.getIndexPage(model);

        // THEN

        //test view name
        assertEquals("recipeapp/index", viewName);
        // test num times call RecipeService.getRecipes()
        verify(recipeService, times(1)).getRecipes();
        // test num times call Model.addAttribute()
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
    }

    @Test
    public void getIndexPage2() throws Exception {
        // GIVEN
        Recipe recipe1 = new Recipe();
        recipe1.setId(100L);
        Recipe recipe2 = new Recipe();
        recipe2.setId(101L);
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe1);
        recipesData.add(recipe2);

        when(recipeService.getRecipes()).thenReturn(recipesData);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // WHEN
        String viewName = indexController.getIndexPage(model);

        // THEN
        assertEquals("recipeapp/index", viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setValueCaptor = argumentCaptor.getValue();
        assertEquals(2, setValueCaptor.size());
    }
}