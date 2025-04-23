package it.polito.cs.hogwartsartifactsonline.wizard;

public class WizardNotFoundException extends RuntimeException {
    public WizardNotFoundException(String id) {
        super("Could not find wizard by id " + id + " :(");
    }
}
