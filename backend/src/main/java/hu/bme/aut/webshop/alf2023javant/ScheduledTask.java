package hu.bme.aut.webshop.alf2023javant;

import hu.bme.aut.webshop.alf2023javant.service.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Autowired
    private EmailSenderService emailSenderService;

    private static final Logger logger = LoggerFactory.getLogger(Alf2023JavanTApplication.class);

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void sendScheduledEmail() {
        logger.info("The app is up and running!");
        emailSenderService.sendEmail("szigetidonat@gmail.com", "Webshop", "The application is still up and running!");
    }
}

