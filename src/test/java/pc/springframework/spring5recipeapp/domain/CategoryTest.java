package pc.springframework.spring5recipeapp.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {
    private Category category;

    @Before
    public void setUp() throws Exception {
        category = new Category();
    }

    @Test
    public void getId() throws Exception {
        Long idValue = 4L;
        category.setId(idValue);
        assertEquals(idValue, category.getId());
    }

    @Test
    public void getDescription() throws Exception {
        String descValue = "My description";
        category.setDescription(descValue);
        assertEquals(descValue, category.getDescription());
    }

    @Test
    public void getRecipes() throws Exception {
    }

}