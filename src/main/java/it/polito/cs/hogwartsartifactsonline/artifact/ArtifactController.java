package it.polito.cs.hogwartsartifactsonline.artifact;

import it.polito.cs.hogwartsartifactsonline.artifact.converter.ArtifactDtoToArtifactConverter;
import it.polito.cs.hogwartsartifactsonline.artifact.converter.ArtifactToArtifactDtoConverter;
import it.polito.cs.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import it.polito.cs.hogwartsartifactsonline.system.Result;
import it.polito.cs.hogwartsartifactsonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/artifacts")
public class ArtifactController {

    private final ArtifactService artifactService;

    private final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;

    private final ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;


    public ArtifactController(ArtifactService artifactService, ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter, ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
        this.artifactDtoToArtifactConverter = artifactDtoToArtifactConverter;
    }

    @GetMapping("/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId) {
        Artifact foundArtifact = artifactService.findArtifactById(artifactId);
        ArtifactDto artifactDto = artifactToArtifactDtoConverter.convert(foundArtifact);
        //The controller makes sure to transform the Result POJO to serialized JSON object.
        return new Result(true, StatusCode.SUCCESS, "Find one success", artifactDto);
    }

    @GetMapping()
    public Result findAllArtifacts() {
        List<ArtifactDto> artifactsDto = artifactService.findAllArtifacts().stream().map(artifactToArtifactDtoConverter::convert).collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find all success", artifactsDto);
    }

    @PostMapping()
    public Result addArtifact(@Valid @RequestBody ArtifactDto artifactDto) {
        Artifact artifact = artifactDtoToArtifactConverter.convert(artifactDto);
        Artifact savedArtifact = artifactService.saveArtifact(artifact);
        ArtifactDto savedArtifactDto = artifactToArtifactDtoConverter.convert(savedArtifact);
        return new Result(true, StatusCode.SUCCESS, "Add one success", savedArtifactDto);
    }

    @PutMapping("/{artifactId}")
    public Result updateArtifact(@Valid @RequestBody ArtifactDto artifactDto, @PathVariable String artifactId) {
        Artifact artifact = artifactDtoToArtifactConverter.convert(artifactDto);
        Artifact result = artifactService.updateArtifact(artifact, artifactId);
        ArtifactDto resultDto = artifactToArtifactDtoConverter.convert(result);
        return new Result(true, StatusCode.SUCCESS, "Update one success", resultDto);
    }

    @DeleteMapping("/{artifactId}")
    public Result deleteArtifact(@PathVariable String artifactId) {
        artifactService.deleteArtifact(artifactId);
        return new Result(true, StatusCode.SUCCESS, "Deleted artifact with id " + artifactId);
    }
    
}
