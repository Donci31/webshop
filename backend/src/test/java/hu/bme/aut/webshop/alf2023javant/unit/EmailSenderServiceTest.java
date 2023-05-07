package hu.bme.aut.webshop.alf2023javant.unit;

import hu.bme.aut.webshop.alf2023javant.service.EmailSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EmailSenderServiceTest {
    @Mock
    JavaMailSender mailSender;

    @InjectMocks
    EmailSenderService emailSenderService;


    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testSendEmail() {
        // Given
        String to = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        // When
        emailSenderService.sendEmail(to, subject, body);

        // Then
        ArgumentCaptor<SimpleMailMessage> emailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(emailCaptor.capture());
        SimpleMailMessage sentEmail = emailCaptor.getValue();

        assertEquals(to, Objects.requireNonNull(sentEmail.getTo())[0]);
        assertEquals(subject, sentEmail.getSubject());
        assertEquals(body, sentEmail.getText());
    }
}
