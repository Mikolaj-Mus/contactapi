package io.mikolajmus.contactapi.repo;

import io.mikolajmus.contactapi.domain.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ContactRepoTests {

    @Autowired
    private ContactRepo contactRepo;

    @Test
    public void contactRepoSaveAllReturnContact() {

        // Arrange
        Contact contact = Contact.builder()
                .name("John Smith")
                .email("johnsmith@gmail.com")
                .title("Tester")
                .phone("783 456 909")
                .address("USA")
                .status("Active").build();

        // Act
        Contact savedContact = contactRepo.save(contact);

        // Assert
        assertThat(savedContact).isNotNull();
        assertThat(savedContact.getId()).isNotNull();
    }
}
