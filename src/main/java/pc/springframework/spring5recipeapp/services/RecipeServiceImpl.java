package pc.springframework.spring5recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pc.springframework.spring5recipeapp.domain.Recipe;
import pc.springframework.spring5recipeapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("### getRecipes()");
        Set<Recipe> recipeSet = new HashSet<>();
        //recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        recipeRepository.findAll().forEach(recipeSet::add);
        return recipeSet;
    }
}
