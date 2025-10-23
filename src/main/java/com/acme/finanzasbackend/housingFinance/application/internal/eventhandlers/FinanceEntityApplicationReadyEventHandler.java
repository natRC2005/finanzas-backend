package com.acme.finanzasbackend.housingFinance.application.internal.eventhandlers;

import com.acme.finanzasbackend.housingFinance.domain.model.commands.SeedFinanceEntitiesCommand;
import com.acme.finanzasbackend.housingFinance.domain.services.FinanceEntityCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class FinanceEntityApplicationReadyEventHandler {
    private final FinanceEntityCommandService financeEntityCommandService;
    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceEntityApplicationReadyEventHandler.class);

    public FinanceEntityApplicationReadyEventHandler(FinanceEntityCommandService financeEntityCommandService) {
        this.financeEntityCommandService = financeEntityCommandService;
    }

    @Order(2)
    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Starting to verify if finance entity seeding is needed for {} at {}", applicationName, currentTimestamp());
        var seedFinanceEntityCommand = new SeedFinanceEntitiesCommand();
        financeEntityCommandService.handle(seedFinanceEntityCommand);
        LOGGER.info("Finance entity seeding verification finished for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
