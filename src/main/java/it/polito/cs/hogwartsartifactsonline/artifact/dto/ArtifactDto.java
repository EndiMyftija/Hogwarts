package it.polito.cs.hogwartsartifactsonline.artifact.dto;

import it.polito.cs.hogwartsartifactsonline.wizard.dto.WizardDto;

public record ArtifactDto(String id,
                          String name,
                          String description,
                          String imageUrl,
                          WizardDto wizardDto) {

}
