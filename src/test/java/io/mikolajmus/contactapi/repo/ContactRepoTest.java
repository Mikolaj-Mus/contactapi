package io.mikolajmus.contactapi.repo;

import io.mikolajmus.contactapi.domain.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ContactRepoTest {

    @Autowired
    private ContactRepo contactRepo;

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
    public void contactRepoSaveAllReturnContact() {

        Contact savedContact = contactRepo.save(contact);

        assertThat(savedContact).isNotNull();
        assertThat(savedContact.getId()).isNotNull();
    }

    @Test
    public void contactRepoGetAllReturnContacts() {

        Contact contact2 = Contact.builder()
                .name("Adam Buster")
                .email("adambuster@gmail.com")
                .title("DevOps")
                .phone("876 355 009")
                .address("Canada")
                .status("Inactive").build();

        contactRepo.save(contact);
        contactRepo.save(contact2);

        List<Contact> contactList = contactRepo.findAll();

        assertThat(contactList).isNotNull();
        assertThat(contactList.size()).isEqualTo(2);
    }

    @Test
    public void contactRepoFindByIdReturnContact() {

        contactRepo.save(contact);

        Contact savedContact = contactRepo.findById(contact.getId()).get();

        assertThat(savedContact).isNotNull();

    }

    @Test
    public void contactRepoUpdateContactReturnContact() {

        contactRepo.save(contact);

        Contact savedContact = contactRepo.findById(contact.getId()).get();
        savedContact.setName("Adam Buster");

        Contact updatedContact = contactRepo.save(savedContact);

        assertThat(updatedContact.getName()).isNotNull();

    }

    @Test
    public void contactRepoDeleteContactReturnContactIsEmpty() {

        contactRepo.save(contact);

        contactRepo.deleteById(contact.getId());
        Optional<Contact> optionalContact = contactRepo.findById(contact.getId());

        assertThat(optionalContact).isEmpty();

    }
}
