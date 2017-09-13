package pc.springframework.spring5recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pc.springframework.spring5recipeapp.commands.IngredientCommand;
import pc.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import pc.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import pc.springframework.spring5recipeapp.domain.Ingredient;
import pc.springframework.spring5recipeapp.domain.Recipe;
import pc.springframework.spring5recipeapp.repositories.IngredientRepository;
import pc.springframework.spring5recipeapp.repositories.RecipeRepository;
import pc.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientRepository ingredientRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found! id:" + recipeId);
            throw new RuntimeException("Recipe not found! id:" + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            log.error("Ingredient not found! id:" + ingredientId);
            throw new RuntimeException("Ingredient not found! id:" + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found! id:" + command.getRecipeId());
            throw new RuntimeException("Recipe not found! id:" + command.getRecipeId());
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();
        if (ingredientOptional.isPresent()) {
            //update ingredient
            Ingredient ingredientToUpdate = ingredientOptional.get();
            ingredientToUpdate.setDescription(command.getDescription());
            ingredientToUpdate.setAmount(command.getAmount());
            ingredientToUpdate.setUom(unitOfMeasureRepository
                    .findById(command.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
        } else {
            //new ingredient
            Ingredient newIngredient = ingredientCommandToIngredient.convert(command);
            newIngredient.setRecipe(recipe);
            recipe.addIngredient(newIngredient);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);
        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                .findFirst();
        if (!savedIngredientOptional.isPresent()) {
            //not totally safe... But best guess
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                    .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                    .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                    .findFirst();
        }

        //to do check for fail
        return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
    }

    @Override
    @Transactional
    public void deleteById(Long recipeId, Long idToDelete) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(idToDelete))
                    .findFirst();
            if (ingredientOptional.isPresent()) {
                Ingredient ingredientToDelete = ingredientOptional.get();
                recipe.removeIngredient(ingredientToDelete);
                recipeRepository.save(recipe);
                ingredientRepository.deleteById(ingredientToDelete.getId());
            } else {
                log.debug("Ingredient not found! id:" + idToDelete);
            }
        } else {
            log.debug("Recipe not found! id:" + recipeId);
        }

    }

}
