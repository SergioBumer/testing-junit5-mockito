package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

  @Mock()
  SpecialtyRepository specialtyRepository;

  @InjectMocks
  SpecialitySDJpaService specialitySDJpaService;

  @BeforeEach
  void setUp() {
  }

  @Test
  void testDeleteById() {
    for (int i = 0; i < 50; i++) {
      specialitySDJpaService.deleteById(1l);
    }
    verify(specialtyRepository, atLeastOnce()).deleteById(1l);
  }

  @Test
  void testDeleteByIdAtLeast() {
    for (int i = 0; i < 50; i++) {
      specialitySDJpaService.deleteById(1l);
    }
    verify(specialtyRepository, times(50)).deleteById(1l);
  }

  @Test
  void testDeleteByIdAtMost() {
    for (int i = 0; i < 5; i++) {
      specialitySDJpaService.deleteById(1l);
    }
    verify(specialtyRepository, atMost(6)).deleteById(1l);
  }

  @Test
  void testDeleteByIdNever() {
    for (int i = 0; i < 50; i++) {
      specialitySDJpaService.deleteById(2l);
    }
    verify(specialtyRepository, atLeastOnce()).deleteById(any());
  }


  @Test
  void testDelete() {
    specialitySDJpaService.delete(new Speciality());
  }

  @Test
  void testFindByIdBDD() {
    //given
    Speciality speciality = new Speciality();
    given(specialtyRepository.findById(anyLong())).willReturn(Optional.of(speciality));

    //when
    Speciality foundSpecialty = specialitySDJpaService.findById(1l);
    assertThat(foundSpecialty).isNotNull();

    //then
    then(specialtyRepository).should().findById(anyLong());
    then(specialtyRepository).should(times(1)).findById(anyLong());
    then(specialtyRepository).shouldHaveNoMoreInteractions();

  }

  @Test
  void testFindById() {
    Speciality speciality = new Speciality();
    when(specialtyRepository.findById(anyLong())).thenReturn(Optional.of(speciality));

    Speciality foundSpecialty = specialitySDJpaService.findById(1l);
    assertThat(foundSpecialty).isNotNull();

  }

  @Test
  void testDeleteByObject() {
    Speciality speciality = new Speciality();

    specialitySDJpaService.delete(speciality);

    verify(specialtyRepository).delete(any(Speciality.class));
  }

  @Test
  void testDoThrow() {
    doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any(Speciality.class));

    assertThrows(RuntimeException.class, () -> specialitySDJpaService.delete(new Speciality()));
    verify(specialtyRepository).delete(any(Speciality.class));
  }

  @Test
  void testDoThrowBDD() {
    given(specialtyRepository.findById(1L)).willThrow(new RuntimeException("boom"));

    assertThrows(RuntimeException.class, () -> specialitySDJpaService.findById(1L));
    then(specialtyRepository).should().findById(1L);
  }

  @Test
  void testDeleteBDD() {
    willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());
    assertThrows(RuntimeException.class, () -> specialitySDJpaService.delete(new Speciality()));
  }

  @Test
  void testSaveLambda() {

    final String MATCH_ME = "MATCH_ME";
    Speciality speciality = new Speciality();
    speciality.setDescription(MATCH_ME);
    Speciality savedSpeciaty = new Speciality();
    savedSpeciaty.setId(1L);

    // Need to mock to only return on match MATCH_ME string
    given(specialtyRepository.save(argThat(argument -> argument.getDescription()
            .equals(MATCH_ME)))).willReturn(savedSpeciaty);
    //when
    Speciality returnedSpecialty = specialitySDJpaService.save(speciality);

    //then

    assertThat(returnedSpecialty.getId()).isEqualTo(1L);
  }

  @Test
  void testSaveLambdaNoMatch() {

    final String MATCH_ME = "MATCH_ME";
    Speciality speciality = new Speciality();
    speciality.setDescription("Not a match");
    Speciality savedSpeciaty = new Speciality();
    savedSpeciaty.setId(1L);

    // Need to mock to only return on match MATCH_ME string
    given(specialtyRepository.save(argThat(argument -> argument.getDescription()
            .equals(MATCH_ME)))).willReturn(savedSpeciaty);
    //when
    Speciality returnedSpecialty = specialitySDJpaService.save(speciality);

    //then

    assertNull(returnedSpecialty);
  }
}