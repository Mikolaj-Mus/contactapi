package io.mikolajmus.contactapi.service;

import io.mikolajmus.contactapi.domain.Contact;
import io.mikolajmus.contactapi.repo.ContactRepo;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepo contactRepo;
    @InjectMocks
    private ContactService contactService;

    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = Contact.builder()
                .id("de3e7fda-ae35-4a41-8376-6f6dc225580d")
                .name("John Smith")
                .email("johnsmith@gmail.com")
                .title("Tester")
                .phone("783 456 909")
                .address("USA")
                .status("Active").build();
    }

    @Test
    public void ContactServiceCreateContactReturnContact() {

        when(contactRepo.save(any(Contact.class))).thenReturn(contact);

        Contact savedContact = contactService.createContact(contact);

        assertThat(savedContact).isNotNull();

    }

    @Test
    public void ContactServiceGetAllContactsReturn() {
        int page = 0;
        int size = 10;

        Page<Contact> mockedPage = Mockito.mock(Page.class);

        when(mockedPage.getContent()).thenReturn(Arrays.asList(
                contact,
                Contact.builder()
                        .name("Adam Buster")
                        .email("adambuster@gmail.com")
                        .title("DevOps")
                        .phone("876 355 009")
                        .address("Canada")
                        .status("Inactive").build()
        ));

        when(contactRepo.findAll(PageRequest.of(page, size, Sort.by("name"))))
                .thenReturn(mockedPage);

        Page<Contact> resultPage = contactService.getAllContacts(page, size);

        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getContent()).hasSize(2);
    }


    @Test
    public void contactServiceGetContactReturnContact() {
        System.out.println(contact.getId());

        when(contactRepo.findById(contact.getId())).thenReturn(Optional.of(contact));

        Contact returnedContact = contactService.getContact(contact.getId());

        assertThat(returnedContact).isNotNull();
    }

    @Test
    void contactServiceUploadPhotoReturnPhotoUrl() {

        String originalFilename = "test.jpg";

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setContextPath("/contacts/image/");
        ServletRequestAttributes attrs = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attrs);

        MockMultipartFile file = new MockMultipartFile("file", originalFilename, "image/jpeg", "test data".getBytes());

        when(contactRepo.findById(contact.getId())).thenReturn(Optional.of(contact));

        when(contactRepo.save(any(Contact.class))).thenReturn(contact);

        String photoUrl = contactService.uploadPhoto(contact.getId(), file);

        assertThat(photoUrl).isNotNull();

    }
}
