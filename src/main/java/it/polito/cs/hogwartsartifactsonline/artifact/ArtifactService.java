package it.polito.cs.hogwartsartifactsonline.artifact;

import it.polito.cs.hogwartsartifactsonline.artifact.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ArtifactService {

    private final ArtifactRepository artifactRepository;

    private final IdWorker idWorker;

    public ArtifactService(ArtifactRepository artifactRepository, IdWorker idWorker) {
        this.artifactRepository = artifactRepository;
        this.idWorker = idWorker;
    }

    public Artifact findArtifactById(String artifactId) {
        return artifactRepository.findById(artifactId).orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

    public Artifact saveArtifact(Artifact artifact) {
        artifact.setId(idWorker.nextId() + "");
        return artifactRepository.save(artifact);
    }

    public Artifact updateArtifact(Artifact artifact, String artifactId) {
        Artifact oldArtifact = this.artifactRepository.findById(artifactId).get();
        oldArtifact.setName(artifact.getName());
        oldArtifact.setDescription(artifact.getDescription());
        oldArtifact.setImageUrl(artifact.getImageUrl());
        oldArtifact.setOwner(artifact.getOwner());
        return this.artifactRepository.save(oldArtifact);
        //save() is pretty smart. If the id exists already then it updates, otherwise it inserts into the db.
    }
}
