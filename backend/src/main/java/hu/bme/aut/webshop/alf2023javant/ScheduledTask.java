package hu.bme.aut.webshop.alf2023javant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(Alf2023JavanTApplication.class);

    @Scheduled(fixedRate = 60 * 1000)
    public void sendScheduledEmail() {
        logger.info("The app is up and running!");
    }
}

