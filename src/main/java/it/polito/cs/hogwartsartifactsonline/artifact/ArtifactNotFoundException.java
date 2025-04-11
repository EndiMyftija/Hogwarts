package it.polito.cs.hogwartsartifactsonline.artifact;

public class ArtifactNotFoundException extends RuntimeException {
    public ArtifactNotFoundException(String id) {
        super("Could not find artifact by id " + id + " :(");
    }
}
