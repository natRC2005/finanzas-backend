package com.acme.finanzasbackend.housingFinance.application.internal.commandservices;

import com.acme.finanzasbackend.housingFinance.domain.model.aggregates.FinanceEntity;
import com.acme.finanzasbackend.housingFinance.domain.model.commands.SeedFinanceEntitiesCommand;
import com.acme.finanzasbackend.housingFinance.domain.model.valueobjects.FinanceEntityType;
import com.acme.finanzasbackend.housingFinance.domain.services.FinanceEntityCommandService;
import com.acme.finanzasbackend.housingFinance.infrastructure.persistence.jpa.repositories.FinanceEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceEntityCommandServiceImpl implements FinanceEntityCommandService {
    private final FinanceEntityRepository financeEntityRepository;

    public FinanceEntityCommandServiceImpl(FinanceEntityRepository financeEntityRepository) {
        this.financeEntityRepository = financeEntityRepository;
    }

    @Override
    public void handle(SeedFinanceEntitiesCommand command) {
        var defaultFinanceEntities = List.of(
                new FinanceEntity("BCP Banco de Crédito del Perú", FinanceEntityType.BANCA_MULTIPLE, null, 427600.00,
                        32000.00, 319590.00, 1500.00, null, null,
                        true, null, null, null, null,
                        false, null, null, true),
                new FinanceEntity("BBVA Perú", FinanceEntityType.BANCA_MULTIPLE, null, 362100.00,
                        10000.00, 319590.00, null, 7.5, 10.0,
                        true, 6, 6, 6, 300,
                        false, null, null, false),
                new FinanceEntity("Interbank", FinanceEntityType.BANCA_MULTIPLE, null, 68800.00,
                        null, null, 1000.00, 7.5, 7.5,
                        true, 6, 3, 60, 300,
                        false, null, null, true),
                new FinanceEntity("Scotiabank", FinanceEntityType.BANCA_MULTIPLE, null, 355100.00,
                        null, 355100.00, 1200.00, null, null,
                        true, null, null, null, null,
                        true, null, null, false),
                new FinanceEntity("BanBif", FinanceEntityType.BANCA_MULTIPLE, null, 488800.00,
                        68800.00, null, null, 7.5, null,
                        true, 6, 6, null, 300,
                        false, 1.0, 2.0, true),
                new FinanceEntity("Bancom", FinanceEntityType.BANCA_MULTIPLE, null, 488800.00,
                        null, null, null, 10.0, null,
                        true, null, null, null, null,
                        false, null, null, false),
                new FinanceEntity("Banco GNB", FinanceEntityType.BANCA_MULTIPLE, null, 374500.00,
                        null, null, null, 10.0, 30.0,
                        true, null, null, 60, null,
                        false, null, null, false),
                new FinanceEntity("CMAC Huancayo", FinanceEntityType.CMAC, null, 343900.00,
                        null, null, null, null, null,
                        true, null, null, null, 300,
                        true, null, null, false),
                new FinanceEntity("CMAC Ica", FinanceEntityType.CMAC, null, null,
                        null, null, null, 7.5, 7.5,
                        true, 6, null, 60, 300,
                        true, null, null, true),
                new FinanceEntity("CMAC Cusco", FinanceEntityType.CMAC, null, 67400.00,
                        null, null, null, 7.5, 7.5,
                        true, null, null, 60, 240,
                        false, null, null, true),
                new FinanceEntity("CMAC Trujillo", FinanceEntityType.CMAC, null, null,
                        null, null, null, 10.0, 10.0,
                        false, null, null, 60, 240,
                        true, null, null, false),
                new FinanceEntity("CMAC Maynas", FinanceEntityType.CMAC, null, null,
                        null, null, null, 10.0, 30.0,
                        true, 6, 6, 60, 300,
                        false, null, null, true),
                new FinanceEntity("CMAC Arequipa", FinanceEntityType.CMAC, null, 59800.00,
                        90.0, 90.0, 0.00, 10.0, 10.0,
                        true, null, null, null, 240,
                        false, 2.0, 2.0, false),
                new FinanceEntity("CMAC Piura", FinanceEntityType.CMAC, null, 68800.00,
                        90.0, 90.0, 0.00, 7.5, 7.5,
                        true, null, null, 60, 300,
                        false, 2.0, 0.5, true),
                new FinanceEntity("Financiera Efectiva", FinanceEntityType.FINANCIERA, null, 68800.00,
                        90.0, 90.0, null, null, null,
                        true, 60, 60, 60, 300,
                        true, null, null, false),
                new FinanceEntity("Financiera Santander", FinanceEntityType.FINANCIERA, null, null,
                        null, null, 1200.00, 10.0, null,
                        true, null, null, null, 300,
                        false, null, null, true),
                new FinanceEntity("EC Vivela", FinanceEntityType.EMPRESA_DE_CREDITO, null, null,
                        null, null, 1000.00, 10.0, null,
                        true, 60, 60, 60, 300,
                        false, null, null, true)
        );
        defaultFinanceEntities.forEach(financeEntity -> {
            if (!financeEntityRepository.existsByName(financeEntity.getName())) {
                financeEntityRepository.save(financeEntity);
            }
        });
    }
}
