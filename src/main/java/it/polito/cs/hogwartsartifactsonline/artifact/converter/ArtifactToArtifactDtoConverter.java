package it.polito.cs.hogwartsartifactsonline.artifact.converter;

import it.polito.cs.hogwartsartifactsonline.artifact.Artifact;
import it.polito.cs.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import it.polito.cs.hogwartsartifactsonline.wizard.converter.WizardToWizardDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactToArtifactDtoConverter implements Converter<Artifact, ArtifactDto> {

    WizardToWizardDtoConverter wizardToWizardDtoConverter;

    public ArtifactToArtifactDtoConverter(WizardToWizardDtoConverter wizardToWizardDtoConverter) {
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }

    @Override
    public ArtifactDto convert(Artifact source) {
        return new ArtifactDto(source.getId(),
                source.getName(),
                source.getDescription(),
                source.getImageUrl(),
                source.getOwner() != null
                                     ? this.wizardToWizardDtoConverter.convert(source.getOwner())
                                     : null);
    }
}
