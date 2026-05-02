package com.onlineakimbank.accountservice.service;

import com.onlineakimbank.accountservice.entity.enums.Currency;
import com.onlineakimbank.accountservice.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

@Service
@RequiredArgsConstructor
public class CbXmlCurrencyConverter implements CurrencyConverter {

    private final CurrencyRateRepository repository;

    @Override
    public BigDecimal convert(BigDecimal amount, Currency from, Currency to) {
        if (from == to) return amount;
        return amount.multiply(getRate(from, to));
    }

    @Override
    public BigDecimal getRate(Currency from, Currency to) {
        if (from == to) return BigDecimal.ONE;
        BigDecimal rate = repository.getRate(from.name(), to.name());
        if (rate == null) throw new IllegalArgumentException("[Course not found: " + from + " -> " + to + "]");
        return rate;
    }

    public void refreshRates() {
        try {
            URL url = new URL("https://www.cbr-xml-daily.ru/daily.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openStream());
            NodeList valutes = doc.getElementsByTagName("Valute");

            for (int i = 0; i < valutes.getLength(); i++) {
                Element valute = (Element) valutes.item(i);
                String charCode = valute.getElementsByTagName("CharCode").item(0).getTextContent();
                String valueStr = valute.getElementsByTagName("Value").item(0).getTextContent();
                String nominalStr = valute.getElementsByTagName("Nominal").item(0).getTextContent();

                BigDecimal value = new BigDecimal(valueStr.replace(',', '.'));
                int nominal = Integer.parseInt(nominalStr);
                BigDecimal rate = value.divide(new BigDecimal(nominal), 6, BigDecimal.ROUND_HALF_UP);

                try {
                    Currency currency = Currency.valueOf(charCode);
                    repository.saveRate(currency.name(), "RUB", rate);
                    repository.saveRate("RUB", currency.name(), BigDecimal.ONE.divide(rate, 6, BigDecimal.ROUND_HALF_UP));
                } catch (IllegalArgumentException ignored) {}
            }

            repository.saveRate("RUB", "RUB", BigDecimal.ONE);

        } catch (Exception e) {
            throw new RuntimeException("[Failed to load Central Bank of the Russian Federation exchange rates]", e);
        }
    }
}
