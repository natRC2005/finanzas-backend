package com.acme.finanzasbackend.shared.application.internal.eventhandlers;

import com.acme.finanzasbackend.shared.domain.model.commands.SeedCurrencyCommand;
import com.acme.finanzasbackend.shared.domain.services.CurrencyCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ApplicationReadyEventHandler {
    private final CurrencyCommandService currencyCommandService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);

    public ApplicationReadyEventHandler(CurrencyCommandService currencyCommandService) {
        this.currencyCommandService = currencyCommandService;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Starting to verify if currency seeding is needed for {} at {}", applicationName, currentTimestamp());
        var seedCurrencyCommand = new SeedCurrencyCommand();
        currencyCommandService.handle(seedCurrencyCommand);
        LOGGER.info("Currency seeding verification finished for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
