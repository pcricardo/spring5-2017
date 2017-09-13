package pc.springframework.spring5recipeapp.converters;

import org.junit.Before;
import org.junit.Test;
import pc.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import pc.springframework.spring5recipeapp.domain.UnitOfMeasure;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    private static final String DESCRIPTION = "description";
    private static final Long LONG_VALUE = new Long(1L);

    private UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullParamter() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }
    @Test
    public void convert() throws Exception {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);

        //when
        UnitOfMeasureCommand uomCommand = converter.convert(uom);

        //then
        assertEquals(LONG_VALUE, uomCommand.getId());
        assertEquals(DESCRIPTION, uomCommand.getDescription());
    }

}