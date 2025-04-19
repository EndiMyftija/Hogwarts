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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    void testFindAllSuccess() {
        //given
        List<Artifact> artifacts = new ArrayList<>();
        Artifact a1 = new Artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        a1.setImageUrl("ImageUrl");
        artifacts.add(a1);

        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("ImageUrl");
        artifacts.add(a2);

        Artifact a3 = new Artifact();
        a3.setId("1250808601744904193");
        a3.setName("Elder Wand");
        a3.setDescription("The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.");
        a3.setImageUrl("ImageUrl");
        artifacts.add(a3);

        Artifact a4 = new Artifact();
        a4.setId("1250808601744904194");
        a4.setName("The Marauder's Map");
        a4.setDescription("A magical map of Hogwarts created by Remus Lupin, Peter Pettigrew, Sirius Black, and James Potter while they were students at Hogwarts.");
        a4.setImageUrl("ImageUrl");
        artifacts.add(a4);

        Artifact a5 = new Artifact();
        a5.setId("1250808601744904195");
        a5.setName("The Sword Of Gryffindor");
        a5.setDescription("A goblin-made sword adorned with large rubies on the pommel. It was once owned by Godric Gryffindor, one of the medieval founders of Hogwarts.");
        a5.setImageUrl("ImageUrl");
        artifacts.add(a5);

        Artifact a6 = new Artifact();
        a6.setId("1250808601744904196");
        a6.setName("Resurrection Stone");
        a6.setDescription("The Resurrection Stone allows the holder to bring back deceased loved ones, in a semi-physical form, and communicate with them.");
        a6.setImageUrl("ImageUrl");
        artifacts.add(a6);

        given(artifactRepository.findAll()).willReturn(artifacts);

        //when
        List<Artifact> result = this.artifactService.findAllArtifacts();

        //then
        assertThat(result.get(0).getId()).isEqualTo("1250808601744904191");
        assertThat(result.get(0).getName()).isEqualTo("Deluminator");
        assertThat(result.get(0).getDescription()).isEqualTo("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");

        assertThat(result.get(5).getId()).isEqualTo("1250808601744904196");
        assertThat(result.get(5).getName()).isEqualTo("Resurrection Stone");
        assertThat(result.get(5).getDescription()).isEqualTo("The Resurrection Stone allows the holder to bring back deceased loved ones, in a semi-physical form, and communicate with them.");


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

    @Test
    public void testDeleteSuccess() {
        //given
        Artifact artifact = new Artifact();
        artifact.setId("123456789");
        artifact.setName("Invisibility Cloak");
        artifact.setDescription("An invisibility cloak is used to make the wearer invisible.");
        artifact.setImageUrl("ImageUrl");

        given(artifactRepository.findById("123456789")).willReturn(Optional.of(artifact));
        doNothing().when(artifactRepository).delete(artifact);

        //when
        artifactService.deleteArtifact("123456789");

        //then
        verify(artifactRepository, times(1)).findById("123456789");
        verify(artifactRepository, times(1)).delete(artifact);
    }

    @Test
    public void testDeleteNotFound() {
        //given
        given(artifactRepository.findById("123456789")).willReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> artifactService.deleteArtifact("123456789"));

        //then
        verify(artifactRepository, times(1)).findById("123456789");
        assertThat(thrown).isInstanceOf(ArtifactNotFoundException.class);
    }
}