package pc.springframework.spring5recipeapp.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import pc.springframework.spring5recipeapp.domain.UnitOfMeasure;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findByDescriptionTeaspoon() throws Exception {
        Optional<UnitOfMeasure> unit = unitOfMeasureRepository.findByDescription("Teaspoon");
        assertEquals("Teaspoon", unit.get().getDescription());
    }

    @Test
    public void findByDescriptionCup() throws Exception {
        Optional<UnitOfMeasure> unit = unitOfMeasureRepository.findByDescription("Cup");
        assertEquals("Cup", unit.get().getDescription());
    }
}