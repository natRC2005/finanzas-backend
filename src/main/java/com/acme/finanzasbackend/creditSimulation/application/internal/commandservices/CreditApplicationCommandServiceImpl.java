package com.acme.finanzasbackend.creditSimulation.application.internal.commandservices;

import com.acme.finanzasbackend.clientManagement.infrastructure.persistence.jpa.repositories.ClientRepository;
import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.Bonus;
import com.acme.finanzasbackend.creditSimulation.domain.model.aggregates.CreditApplication;
import com.acme.finanzasbackend.creditSimulation.domain.model.commands.CreateCreditApplicationCommand;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.GracePeriod;
import com.acme.finanzasbackend.creditSimulation.domain.model.entities.InterestRate;
import com.acme.finanzasbackend.creditSimulation.domain.services.CreditApplicationCommandService;
import com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories.BonusRepository;
import com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories.CreditApplicationRepository;
import com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories.GracePeriodRepository;
import com.acme.finanzasbackend.creditSimulation.infrastructure.persistence.jpa.repositories.InterestRateRepository;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.HousingState;
import com.acme.finanzasbackend.housingFinance.infrastructure.persistence.jpa.repositories.FinanceEntityRepository;
import com.acme.finanzasbackend.housingFinance.infrastructure.persistence.jpa.repositories.HousingRepository;
import com.acme.finanzasbackend.shared.infrastructure.persistence.jpa.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

@Service
public class CreditApplicationCommandServiceImpl implements CreditApplicationCommandService {
    private final CreditApplicationRepository creditApplicationRepository;
    private final BonusRepository bonusRepository;
    private final ClientRepository clientRepository;
    private final HousingRepository housingRepository;
    private final FinanceEntityRepository financeEntityRepository;
    private final CurrencyRepository currencyRepository;
    private final InterestRateRepository interestRateRepository;
    private final GracePeriodRepository gracePeriodRepository;

    public CreditApplicationCommandServiceImpl(
            CreditApplicationRepository creditApplicationRepository,
            BonusRepository bonusRepository,
            ClientRepository clientRepository,
            HousingRepository housingRepository,
            FinanceEntityRepository financeEntityRepository,
            CurrencyRepository currencyRepository,
            InterestRateRepository interestRateRepository,
            GracePeriodRepository gracePeriodRepository
    ) {
        this.creditApplicationRepository = creditApplicationRepository;
        this.bonusRepository = bonusRepository;
        this.clientRepository = clientRepository;
        this.housingRepository = housingRepository;
        this.financeEntityRepository = financeEntityRepository;
        this.currencyRepository = currencyRepository;
        this.interestRateRepository = interestRateRepository;
        this.gracePeriodRepository = gracePeriodRepository;
    }

    @Override
    public Long handle(CreateCreditApplicationCommand command) {
        var client = clientRepository.findById(command.clientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found with ID: " + command.clientId()));
        var housing = housingRepository.findById(command.housingId())
                .orElseThrow(() -> new IllegalArgumentException("Housing not found with ID: " + command.housingId()));
        var currency = currencyRepository.findById(command.currencyId())
                .orElseThrow(() -> new IllegalArgumentException("Currency not found with ID: " + command.currencyId()));

        if (client.getCurrency() != currency) {
            client.exchangeSalaryCurrency(currency);
            try {
                clientRepository.save(client);
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        if (housing.getCurrency() != currency) {
            housing.exchangeSalePriceCurrency(currency);
            try {
                housingRepository.save(housing);
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex);
            }
        }

        var financeEntity = financeEntityRepository.findById(command.financialEntityId())
                .orElseThrow(() -> new IllegalArgumentException("Financial entity not found with ID: " + command.financialEntityId()));

        var interestRate = new InterestRate(command.interestRateType(), command.interestRatePeriod(), command.interestRatePercentage());

        var bonus = new Bonus(command.isBonusRequired(), housing.getHousingCategory(),
                housing.getSalePrice(), currency, client.getIsIntegrator());

        var gracePeriodValidTime = command.gracePeriodMonths();
        if (housing.getHousingState() == HousingState.NUEVO ||
                housing.getHousingState() == HousingState.SEGUNDA) {
            gracePeriodValidTime = 0;
        }
        var gracePeriod = new GracePeriod(command.gracePeriodType(), gracePeriodValidTime);

        boolean hasAnotherHousingFinancing = creditApplicationRepository.existsByClient(client);

        var creditApplication = new CreditApplication(command, client, housing, currency, financeEntity,
                interestRate, bonus, gracePeriod, hasAnotherHousingFinancing);

        try {
            interestRateRepository.save(interestRate);
            bonusRepository.save(bonus);
            gracePeriodRepository.save(gracePeriod);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }

        try {
            creditApplicationRepository.save(creditApplication);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
        return creditApplication.getId();
    }
}
