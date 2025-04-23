package it.polito.cs.hogwartsartifactsonline;

import it.polito.cs.hogwartsartifactsonline.artifact.Artifact;
import it.polito.cs.hogwartsartifactsonline.wizard.Wizard;
import it.polito.cs.hogwartsartifactsonline.wizard.converter.WizardToWizardDtoConverter;
import it.polito.cs.hogwartsartifactsonline.wizard.dto.WizardDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WizardToWizardDtoConverterTest {

    @Test
    void testWizardToWizardDtoConverter() {
        Wizard wizard = new Wizard();
        wizard.setId(1);
        wizard.setName("Harry Potter");
        wizard.addArtifact(new Artifact());

        WizardToWizardDtoConverter converter = new WizardToWizardDtoConverter();
        WizardDto dto = converter.convert(wizard);

        assertNotNull(dto);
        assertEquals(1, dto.id());
        assertEquals("Harry Potter", dto.name());
    }

}
