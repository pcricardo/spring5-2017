package pc.springframework.spring5recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pc.springframework.spring5recipeapp.commands.IngredientCommand;
import pc.springframework.spring5recipeapp.commands.RecipeCommand;
import pc.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import pc.springframework.spring5recipeapp.services.IngredientService;
import pc.springframework.spring5recipeapp.services.RecipeService;
import pc.springframework.spring5recipeapp.services.UnitOfMeasureService;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }


    @GetMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model) {
        log.debug("### IngredientController - getting ingredients list for recipe id:" + id);

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId, Model model) {
        log.debug("### IngredientController - show recipe ingredient RecipeId:" + recipeId + " IngredientId:" + ingredientId);

        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newRecipeIngredientForm(@PathVariable String recipeId, Model model) {
        log.debug("### IngredientController - new recipe ingredient form RecipeId:" + recipeId);

        RecipeCommand recipeCommand = recipeService.findCommandById(new Long(recipeId));
        //todo raise exception if null

        //init IngredientCommand
        IngredientCommand ingredientCommand = new IngredientCommand();
        //need to return back parent id for hidden form property
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        //need to init uom
        ingredientCommand.setRecipeId(recipeCommand.getId());

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredientForm(@PathVariable String recipeId,
                                             @PathVariable String ingredientId,
                                             Model model) {
        log.debug("### IngredientController - update recipe ingredient form RecipeId:" + recipeId + " IngredientId:" + ingredientId);

        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        log.debug("### IngredientController - saveOrUpdate");

        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(command);

        String view = "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";
        log.debug(view);
        return view;
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteById(@PathVariable String recipeId,
                             @PathVariable String ingredientId) {
        log.debug("### IngredientController - delete ingredient recipe RecipeId:" + recipeId + ", IngredientId:" + ingredientId);

        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
