package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
  private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
  private static final String VIEWS_OWNER_CREATE_SAVED = "redirect:/owners/";


  @Mock
  BindingResult bindingResult;

  @Mock
  OwnerService service;

  @Captor
  ArgumentCaptor<String> stringArgumentCaptor;

  @InjectMocks
  OwnerController ownerController;


  @BeforeEach
  void setUp() {
    given(service.findAllByLastNameLike(stringArgumentCaptor.capture())).willAnswer(invocation -> {

      List<Owner> ownerList = new ArrayList<>();
      String name = invocation.getArgument(0);

      if(name.equals("%Buck%")) {
        ownerList.add(new Owner(1L, "Joe", "Buck"));
        return ownerList;
      }

      throw new RuntimeException("boom");
    });
  }

  @Test
  void testProcessCreationFormReturnViewWhenError() {
    //given
    Owner owner = new Owner(1L, "Joe", "Buck");
    //when
    when(bindingResult.hasErrors()).thenReturn(true);
    //then

    String actual = ownerController.processCreationForm(owner, bindingResult);
    assertEquals(VIEWS_OWNER_CREATE_OR_UPDATE_FORM, actual);
    verify(bindingResult).hasErrors();
  }

  @Test
  void testProcessCreationFormReturnViewSaved() {
    //given
    Owner owner = new Owner(5L, "Joe", "Buck");
    //when
    when(bindingResult.hasErrors()).thenReturn(false);
    when(service.save(any(Owner.class))).thenReturn(owner);
    //then

    String actual = ownerController.processCreationForm(owner, bindingResult);
    assertEquals(VIEWS_OWNER_CREATE_SAVED + owner.getId(), actual);
    verify(service).save(any(Owner.class));
  }

  @Test
  void processfindFormWildcardString() {
    Owner owner = new Owner(1L, "Joe", "Buck");

    //given
    //given(service.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);


    //when
    String viewName = ownerController.processFindForm(owner, bindingResult, null);

    assertThat("%Buck%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
    assertThat("redirect:/owners/1").isEqualToIgnoringCase(stringArgumentCaptor.getValue());

  }
}