package pc.springframework.spring5recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pc.springframework.spring5recipeapp.domain.Recipe;
import pc.springframework.spring5recipeapp.helperfunctions.FilesHelper;
import pc.springframework.spring5recipeapp.repositories.RecipeRepository;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();

            Optional<Byte[]> byteObjects = toObject(file);
            if (byteObjects.isPresent()) {
                recipe.setImage(byteObjects.get());
                recipeRepository.save(recipe);
                log.debug("Image saved");
            }

        } else {
            log.debug("Recipe not found! id:" + recipeId);
        }
    }

    private Optional<Byte[]> toObject(MultipartFile file) {
        Byte[] byteObjects = null;
        try {
            byteObjects = FilesHelper.byteToObject(file.getBytes());
        } catch (IOException e) {
            //todo handle better
            log.error("Error occurred", e);
            e.printStackTrace();
        }
        return Optional.of(byteObjects);
    }

}
