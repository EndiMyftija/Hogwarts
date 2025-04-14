package it.polito.cs.hogwartsartifactsonline.artifact;

import it.polito.cs.hogwartsartifactsonline.artifact.utils.IdWorker;
import it.polito.cs.hogwartsartifactsonline.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {

    @Mock
    ArtifactRepository artifactRepository;

    @Mock
    IdWorker idWorker;

    //InjectMoccks tells java to inject the two mocks (ArtifactRepository and IdWorker into artifactService)
    //because artifactService needs those two objects.
    @InjectMocks
    ArtifactService artifactService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindArtifactByIdSuccess() {
        //Given. Arrange inputs and targets. Define the behaviour of Mock object artifactRepository
        /*
        "id": "123456789",
        "name": "Invisibility Cloak",
        "description": "An invisibility cloak is used to make the wearer invisible.",
        "imageUrl": "ImageUrl"
         */
        Artifact a = new Artifact();
        a.setId("123456789");
        a.setName("Invisibility Cloak");
        a.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a.setImageUrl("ImageUrl");

        Wizard w = new Wizard();
        w.setId(2);
        w.setName("Harry Potter");
        a.setOwner(w);

        given(artifactRepository.findById("123456789")).willReturn(Optional.of(a)); // Defines the behaviour of the mock object

        //When. Act on the target behaviour(artifactService). When steps should cover the method to be tested
        Artifact returnedArtifact = artifactService.findArtifactById("123456789");

        //Then. Assert expected outcomes.
        assertThat(returnedArtifact.getId()).isEqualTo(a.getId()); //Assert that the service returns same thing as repository
        assertThat(returnedArtifact.getName()).isEqualTo(a.getName());
        assertThat(returnedArtifact.getDescription()).isEqualTo(a.getDescription());
        assertThat(returnedArtifact.getImageUrl()).isEqualTo(a.getImageUrl());

        verify(artifactRepository, times(1)).findById("123456789"); //Verify that the findByUd method
        //with that input parameter was called exactly one time
    }

    @Test
    void testFindByIdNotFound() {
        //Given
        given(artifactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        //When
        Throwable thrown = catchThrowable(() -> artifactService.findArtifactById("123456789"));

        //Then
        assertThat(thrown).isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Could not find artifact by id 123456789 :(");

        verify(artifactRepository, times(1)).findById("123456789");
    }

    @Test
    void testSaveSuccess() {
        //given
        Artifact newArtifact = new Artifact();
        newArtifact.setName("Artifact 3");
        newArtifact.setDescription("Description...");
        newArtifact.setImageUrl("ImageUrl...");

        given(artifactRepository.save(newArtifact)).willReturn(newArtifact);
        given(idWorker.nextId()).willReturn(123456L);
        //when
        Artifact returnedArtifact = artifactService.saveArtifact(newArtifact);

        //then
        assertThat(returnedArtifact.getId()).isEqualTo("123456");
        assertThat(returnedArtifact.getName()).isEqualTo(newArtifact.getName());
        assertThat(returnedArtifact.getDescription()).isEqualTo(newArtifact.getDescription());
        assertThat(returnedArtifact.getImageUrl()).isEqualTo(newArtifact.getImageUrl());

        verify(artifactRepository, times(1)).save(newArtifact);
    }

    @Test
    void testUpdateSuccess() {
        //given
        Artifact oldArtifact = new Artifact();
        oldArtifact.setId("123456789");
        oldArtifact.setName("Invisibility Cloak");
        oldArtifact.setDescription("An invisibility cloak is used to make the wearer invisible.");
        oldArtifact.setImageUrl("ImageUrl");

        Artifact updatedArtifact = new Artifact();
        updatedArtifact.setId("123456789");
        updatedArtifact.setName("Invisibility Cloak");
        updatedArtifact.setDescription("A new description.");
        updatedArtifact.setImageUrl("ImageUrl");

        given(artifactRepository.findById("123456789")).willReturn(Optional.of(oldArtifact));
        given(artifactRepository.save(oldArtifact)).willReturn(oldArtifact);
        //when
        Artifact returnedArtifact = artifactService.updateArtifact(updatedArtifact, "123456789");

        //then
        assertThat(returnedArtifact.getId()).isEqualTo(updatedArtifact.getId());
        assertThat(returnedArtifact.getName()).isEqualTo(updatedArtifact.getName());
        assertThat(returnedArtifact.getDescription()).isEqualTo(updatedArtifact.getDescription());
        assertThat(returnedArtifact.getImageUrl()).isEqualTo(updatedArtifact.getImageUrl());

        verify(artifactRepository, times(1)).findById("123456789");
        verify(artifactRepository, times(1)).save(oldArtifact);
    }

    @Test
    void testUpdateNotFound() {
        //given
        Artifact oldArtifact = new Artifact();
        oldArtifact.setId("123456789");
        oldArtifact.setName("Invisibility Cloak");
        oldArtifact.setDescription("An invisibility cloak is used to make the wearer invisible.");
        oldArtifact.setImageUrl("ImageUrl");

        Artifact updatedArtifact = new Artifact();
        updatedArtifact.setId("123456789");
        updatedArtifact.setName("Invisibility Cloak");
        updatedArtifact.setDescription("A new description.");
        updatedArtifact.setImageUrl("ImageUrl");

        given(artifactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());
        //when
        Throwable thrown = catchThrowable(() -> artifactService.updateArtifact(updatedArtifact, "123456789"));

        //Then
        assertThat(thrown).isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Could not find artifact by id 123456789 :(");
    }
}