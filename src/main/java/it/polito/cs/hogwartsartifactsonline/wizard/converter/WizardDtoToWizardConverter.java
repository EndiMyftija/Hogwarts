package it.polito.cs.hogwartsartifactsonline.wizard.converter;

import it.polito.cs.hogwartsartifactsonline.wizard.Wizard;
import it.polito.cs.hogwartsartifactsonline.wizard.dto.WizardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardDtoToWizardConverter implements Converter<WizardDto, Wizard> {

    @Override
    public Wizard convert(WizardDto source) {
        Wizard wizard = new Wizard();
        wizard.setName(source.name());
        return wizard;
    }
}
