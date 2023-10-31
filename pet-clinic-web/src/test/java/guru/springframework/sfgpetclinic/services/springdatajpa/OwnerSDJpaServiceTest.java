package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    public static final String LAST_NAME = "Smith";

    @InjectMocks
    OwnerSDJpaService service;

    @Mock
    private OwnerRepository ownerRepository;

    Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();

    }

    @Test
    void findByLastName() {
        Mockito.when(this.ownerRepository.findByLastName(Mockito.anyString())).thenReturn(returnOwner);

        Owner smith = this.service.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME, smith.getLastName());
        Mockito.verify(this.ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> returnSet = new HashSet<>();
        returnSet.add(Owner.builder().id(1L).build());
        returnSet.add(Owner.builder().id(2L).build());

        Mockito.when(this.ownerRepository.findAll()).thenReturn(returnSet);

        Set<Owner> resultSet = this.service.findAll();

        assertNotNull(resultSet);
        assertEquals(2, resultSet.size());
        Mockito.verify(this.ownerRepository).findAll();
    }

    @Test
    void findById() {
        Mockito.when(this.ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));

        Owner owner = this.service.findById(1L);

        assertNotNull(owner);
        assertEquals(LAST_NAME, owner.getLastName());
        Mockito.verify(this.ownerRepository).findById(any());
    }

    @Test
    void findByIdNotFound() {
        Mockito.when(this.ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Owner owner = this.service.findById(1L);

        assertNull(owner);
        Mockito.verify(this.ownerRepository).findById(any());
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(1L).build();
        Mockito.when(this.ownerRepository.save(any())).thenReturn(returnOwner);

        Owner savedOwner = this.service.save(ownerToSave);

        assertNotNull(savedOwner);
        assertEquals(1L, savedOwner.getId());
        Mockito.verify(this.ownerRepository).save(any());
    }

    @Test
    void delete() {
       this.service.delete(returnOwner);

        Mockito.verify(this.ownerRepository, Mockito.times(1)).delete(any());
    }

    @Test
    void deleteById() {

        this.service.deleteById(1L);

        Mockito.verify(this.ownerRepository, Mockito.times(1)).deleteById(anyLong());
    }
}