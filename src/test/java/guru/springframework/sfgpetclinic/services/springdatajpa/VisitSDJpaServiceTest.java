package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

  @Mock
  VisitRepository visitRepository;

  @InjectMocks
  VisitSDJpaService service;
  @Test
  void findAll() {
    //when
    service.findAll();
    //then
    then(visitRepository).should().findAll();
  }

  @Test
  void findById() {
    service.findById(1l);
    then(visitRepository).should().findById(anyLong());
  }

  @Test
  void save() {
    service.save(new Visit());
    then(visitRepository).should().save(any(Visit.class));
  }

  @Test
  void delete() {
    service.delete(new Visit());
    then(visitRepository).should().delete(any(Visit.class));
  }

  @Test
  void deleteById() {
    service.deleteById(1l);
    then(visitRepository).should().deleteById(anyLong());
  }
}