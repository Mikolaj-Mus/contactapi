package io.mikolajmus.contactapi.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mikolajmus.contactapi.domain.Contact;
import io.mikolajmus.contactapi.service.ContactService;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(controllers = ContactController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void contactControllerCreateContactReturnCreated() throws Exception {

        given(contactService.createContact(
                ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0)
        );

        ResultActions response = mockMvc.perform(post("/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contact)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(contact.getId())));
    }

    @Test
    @Disabled
    void contactControllerGetContactsReturnContacts() throws Exception {

        Page<Contact> mockedPage = new PageImpl<>(Collections.singletonList(contact));
        when(contactService.getAllContacts(0, 10)).thenReturn(mockedPage);

        ResultActions response = mockMvc.perform(get("/contacts")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
        "$.content.size()", CoreMatchers.is(mockedPage.getContent().size())));

    }

    @Test
    public void contactControllerGetContactReturnContact() throws Exception {

        when(contactService.getContact(contact.getId())).thenReturn(contact);

        ResultActions response = mockMvc.perform(get("/contacts/de3e7fda-ae35-4a41-8376-6f6dc225580d")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contact)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(contact.getId())));
    }
}
