package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    private OwnerMapService mapService;
    final Long ownerId = 1L;
    final String lastName = "Smith";

    @BeforeEach
    void setUp() {
        mapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        Owner savedOwner = this.mapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> resultSet = this.mapService.findAll();

        Assertions.assertEquals(1, resultSet.size());
    }

    @Test
    void deleteById() {

        this.mapService.deleteById(ownerId);
        Assertions.assertEquals(0, this.mapService.findAll().size());
    }

    @Test
    void delete() {
        this.mapService.delete(this.mapService.findById(ownerId));
        Assertions.assertEquals(0, this.mapService.findAll().size());
    }

    @Test
    void saveExistingId() {
        Long id = 2L;
        Owner savedOwner = Owner.builder().id(id).build();
        this.mapService.save(savedOwner);
        assertEquals(2, this.mapService.findAll().size());
        assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner savedOwner = this.mapService.save(Owner.builder().build());

        Assertions.assertNotNull(savedOwner);
        Assertions.assertNotNull(savedOwner.getId());
    }

    @Test
    void findById() {
        Owner owner = this.mapService.findById(ownerId);
        Assertions.assertEquals(ownerId, owner.getId());

    }

    @Test
    void findByLastName() {
        Owner smith = this.mapService.findByLastName(lastName);

        assertNotNull(smith);
        assertEquals(ownerId, smith.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner smith = this.mapService.findByLastName("foo");

        assertNull(smith);
    }
}